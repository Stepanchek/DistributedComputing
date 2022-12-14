package server;


import dao.AuthorDAO;
import dao.BookDAO;
import models.Author;
import models.Book;
import util.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class Server {
    private ServerSocket serverSocket;
    private final int port;
    private DataInputStream in;
    private DataOutputStream out;
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;

    public Server(int port) {
        this.port = port;
        this.bookDAO = new BookDAO();
        this.authorDAO = new AuthorDAO();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client accepted");

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            
            while (processQuery());
        }
    }

    private boolean processQuery() {
        try {
            String query = IoUtils.readString(in);
            System.out.println(query);

            switch (query) {
                case "insertAuthor" -> {
                    Author author = IoUtils.readAuthor(in, false);
                    boolean result = authorDAO.insert(author);
                    out.writeBoolean(result);
                }

                case "deleteAuthor" -> {
                    Long id = in.readLong();
                    boolean result = authorDAO.deleteById(id);
                    out.writeBoolean(result);
                }

                case "insertBook" -> {
                    Book book = IoUtils.readBook(in, false);
                    boolean result = bookDAO.insert(book);
                    out.writeBoolean(result);
                }

                case "deleteBook" -> {
                    Long id = in.readLong();
                    boolean result = bookDAO.deleteById(id);
                    out.writeBoolean(result);
                }

                case "updateBook" -> {
                    Book book = IoUtils.readBook(in, true);
                    boolean result = bookDAO.update(book);
                    out.writeBoolean(result);
                }

                case "moveToAnotherAuthor" -> {
                    Long playerId = in.readLong();
                    Long newTeamId = in.readLong();
                    boolean result = bookDAO.moveToAnotherAuthor(playerId, newTeamId);
                    out.writeBoolean(result);
                }

                case "findBooksByAuthorName" -> {
                    String teamName = IoUtils.readString(in);
                    List<Book> result = bookDAO.findByAuthorName(teamName);
                    writeListOfBooks(result);
                }

                case "findAllAuthors" -> {
                    List<Author> teams = authorDAO.findAll();
                    writeListOfAuthors(teams);
                }

                default -> {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void writeListOfBooks(List<Book> books) throws IOException {
        out.writeInt(books.size());

        for (Book book : books) {
            IoUtils.writeBook(out, book, true);
        }
    }

    private void writeListOfAuthors(List<Author> authors) throws IOException {
        out.writeInt(authors.size());

        for (Author author : authors) {
            IoUtils.writeAuthor(out, author, true);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(Constants.PORT);
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}