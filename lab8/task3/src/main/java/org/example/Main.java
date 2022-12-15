package org.example;


import org.example.client.Client;
import org.example.models.Author;
import org.example.models.Book;
import org.example.util.Constants;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        try (Client client = new Client(Constants.HOST, Constants.PORT)) {
            client.connect();
            System.out.println(client.insertAuthor(new Author(null, "Seneca")));
            System.out.println(client.insertBook(new Book(null, 2L, "Kobzar", BigDecimal.ZERO)));
            System.out.println(client.deleteBook(19L));
            System.out.println(client.findBooksByAuthorName("Taras Shevchenko"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}