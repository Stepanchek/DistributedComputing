package org.example.server;


import com.rabbitmq.client.*;

import org.example.dao.AuthorDAO;
import org.example.dao.BookDAO;
import org.example.models.Author;
import org.example.models.Book;
import org.example.util.Constants;
import org.example.util.IoUtils;

import java.io.*;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class Server {
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;
    private final int port;
    private Channel channel;

    public Server(int port) {
        this.authorDAO = new AuthorDAO();
        this.bookDAO = new BookDAO();
        this.port = port;
    }

    public void start() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST);
        factory.setPort(port);

        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(Constants.QUEUE, false, false, false, null);
        channel.queuePurge(Constants.QUEUE);
        channel.basicQos(1);

        System.out.println("Awaiting requests");

        processQuery();
    }

    private void processQuery() throws IOException {
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            DataInputStream in = new DataInputStream(new ByteArrayInputStream(delivery.getBody()));
            ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(outBytes);

            try {
                String query = IoUtils.readString(in);
                System.out.println(query);
                switch (query) {
                    case "insertAuthor" -> {
                        Author toInsert = IoUtils.readAuthor(in, false);
                        boolean result = authorDAO.insert(toInsert);
                        out.writeBoolean(result);
                    }

                    case "insertBook" -> {
                        Book toInsert = IoUtils.readBook(in, false);
                        boolean result = bookDAO.insert(toInsert);
                        out.writeBoolean(result);
                    }

                    case "deleteBook" -> {
                        Long toDelete = in.readLong();
                        boolean result = bookDAO.deleteById(toDelete);
                        out.writeBoolean(result);
                    }

                    case "findBooksByAuthorName" -> {
                        String authorName = IoUtils.readString(in);
                        List<Book> result = bookDAO.findByAuthorName(authorName);
                        writeListOfPlayers(out, result);
                    }
                }
            } catch (RuntimeException e) {
                System.out.println("[.]" + e);
            } finally {
                channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, outBytes.toByteArray());
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };

        channel.basicConsume(Constants.QUEUE, false, deliverCallback, consumerTag -> {});
    }

    private void writeListOfPlayers(DataOutputStream out, List<Book> books) throws IOException {
        out.writeInt(books.size());

        for (Book book : books) {
            IoUtils.writePlayer(out, book, true);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(Constants.PORT);
            server.start();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}