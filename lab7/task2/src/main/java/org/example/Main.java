package org.example;

import org.example.DAO.AuthorDAO;
import org.example.DAO.BookDAO;
import org.example.Models.Author;
import org.example.Models.Book;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        AuthorDAO authorDAO = new AuthorDAO();
        BookDAO bookDAO = new BookDAO();

        //delete Shakespeare book
        bookDAO.deleteById(9L);
        bookDAO.deleteById(10L);

        //Delete Shakhtar
        authorDAO.deleteById(1L);

        //Add to PSG
        bookDAO.insert(new Book(null, 4L, "Neymar", BigDecimal.valueOf(20320)));

        //Update Bushan
        bookDAO.update(new Book(1L, 2L, "Georgy", BigDecimal.ONE));

        //Add Milano
        authorDAO.insert(new Author(null, "Milano"));

        //Update Dynamo
        authorDAO.update(new Author(2L, "Dynamo Kyiv"));

        //Delete Besedin
        bookDAO.deleteById(3L);
    }
}