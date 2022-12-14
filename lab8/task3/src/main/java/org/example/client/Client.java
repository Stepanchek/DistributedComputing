package org.example.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.example.models.Author;
import org.example.models.Book;
import org.example.util.Constants;
import org.example.util.IoUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Client implements AutoCloseable {
    private Channel channel;
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);

        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }

    private DataInputStream call(byte[] params)
            throws IOException, ExecutionException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", Constants.QUEUE, props, params);

        CompletableFuture<ByteArrayInputStream> response = new CompletableFuture<>();
        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.complete(new ByteArrayInputStream(delivery.getBody()));
            }
        }, consumerTag -> {});

        ByteArrayInputStream resultByteArray = response.get();
        channel.basicCancel(ctag);
        return new DataInputStream(resultByteArray);
    }

    public boolean insertAuthor(Author toInsert) throws IOException, ExecutionException, InterruptedException {
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outBytes);
        IoUtils.writeString(out, "insertAuthor");
        IoUtils.writeTeam(out, toInsert, false);
        DataInputStream resultStream = call(outBytes.toByteArray());
        return resultStream.readBoolean();
    }

    public boolean insertBook(Book toInsert) throws IOException, ExecutionException, InterruptedException {
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outBytes);
        IoUtils.writeString(out, "insertBook");
        IoUtils.writePlayer(out, toInsert, false);
        DataInputStream resultStream = call(outBytes.toByteArray());
        return resultStream.readBoolean();
    }

    public boolean deleteBook(Long id) throws IOException, ExecutionException, InterruptedException {
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outBytes);
        IoUtils.writeString(out, "deleteBook");
        out.writeLong(id);
        DataInputStream resultStream = call(outBytes.toByteArray());
        return resultStream.readBoolean();
    }

    public List<Book> findBooksByAuthorName(String teamName)
            throws IOException, ExecutionException, InterruptedException {
        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outBytes);
        IoUtils.writeString(out, "findBooksByAuthorName");
        IoUtils.writeString(out, teamName);
        DataInputStream resultStream = call(outBytes.toByteArray());
        return readBooks(resultStream);
    }

    private List<Book> readBooks(DataInputStream in) throws IOException {
        List<Book> result = new ArrayList<>();
        int listSize = in.readInt();

        for (int i = 0; i < listSize; i++) {
            result.add(IoUtils.readBook(in, true));
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        channel.close();
    }
}