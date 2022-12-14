package client;

import models.Author;
import models.Book;
import util.IoUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
public class Client {
    private Socket socket;
    private final String host;
    private final int port;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public boolean insertAuthor(Author author) throws IOException {
        IoUtils.writeString(out, "insertAuthor");
        IoUtils.writeAuthor(out, author, false);

        return in.readBoolean();
    }

    public boolean deleteAuthor(Long id) throws IOException {
        IoUtils.writeString(out, "deleteAuthor");
        out.writeLong(id);

        return in.readBoolean();
    }

    public boolean insertBook(Book book) throws IOException {
        IoUtils.writeString(out, "insertBook");
        IoUtils.writeBook(out, book, false);

        return in.readBoolean();
    }

    public boolean deleteBook(Long id) throws IOException {
        IoUtils.writeString(out, "deleteBook");
        out.writeLong(id);

        return in.readBoolean();
    }

    public boolean updateBook(Book book) throws IOException {
        IoUtils.writeString(out, "updateBook");
        IoUtils.writeBook(out, book, true);

        return in.readBoolean();
    }

    public boolean moveToAnotherAuthor(Long bookId, Long newAuthorId) throws IOException {
        IoUtils.writeString(out, "moveToAnotherAuthor");
        out.writeLong(bookId);
        out.writeLong(newAuthorId);

        return in.readBoolean();
    }

    public List<Book> findBooksByAuthorName(String authorName) throws IOException {
        IoUtils.writeString(out, "findBooksByAuthorName");
        IoUtils.writeString(out, authorName);

        return readBooks();
    }

    public List<Author> findAllTeams() throws IOException {
        IoUtils.writeString(out, "findAllAuthors");

        return readAuthors();
    }

    private List<Book> readBooks() throws IOException {
        List<Book> result = new ArrayList<>();
        int listSize = in.readInt();

        for (int i = 0; i < listSize; i++) {
            result.add(IoUtils.readBook(in, true));
        }

        return result;
    }

    private List<Author> readAuthors() throws IOException {
        List<Author> result = new ArrayList<>();
        int listSize = in.readInt();

        for (int i = 0; i < listSize; i++) {
            result.add(IoUtils.readAuthor(in, true));
        }

        return result;
    }
}