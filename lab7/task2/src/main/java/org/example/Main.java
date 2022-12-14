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

        // Delete Erich Maria Remark
        authorDAO.deleteById(3L);

        bookDAO.deleteById(17L);

        // add 
        bookDAO.update(new Book(6L, 2L, "Three friends", BigDecimal.ONE));

        // add Tolkien
        authorDAO.insert(new Author(null, "J. Tolkien"));

        // update Franko
        authorDAO.update(new Author(8L, "Ivan Franko"));

        // delete The Running Man
        bookDAO.deleteById(12L);
    }
}