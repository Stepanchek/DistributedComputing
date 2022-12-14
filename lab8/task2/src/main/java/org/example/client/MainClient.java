package org.example.client;


import org.example.models.Author;
import org.example.models.Book;
import org.example.rmi.RmiServer;
import org.example.util.Constants;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MainClient {
    public static void main(String[] args)
            throws MalformedURLException, NotBoundException, RemoteException, InterruptedException {
        RmiServer server = (RmiServer) Naming.lookup(Constants.URL);
        System.out.println("Connected");

        Thread.sleep(4000L);

        server.deleteBook(9L);
        server.deleteBook(10L);

        server.deleteBook(9L);

        //Seneca
        server.deleteAuthor(1L);

        //Add to Steven King
        server.insertBook(new Book(null, 4L, "Lord of the Ring", BigDecimal.valueOf(1550)));

        //Update How to die
        server.updateBook(new Book(4L, 1L, "How to cry", BigDecimal.ONE));

        //Add Zhadan
        server.insertAuthor(new Author(null, "Sergiy Zhadan"));

        //Delete Night in Lisbon
        server.deleteBook(10L);

        //Move King of Lir to Remark
        server.moveToAnotherAuthor(5L, 3L);

        //Real players
        System.out.println(server.findBooksByAuthorName("Real Madrid"));

        //All teams
        System.out.println(server.findAllAuthors());
    }
}