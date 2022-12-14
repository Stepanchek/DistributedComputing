package org.example.rmi;

import org.example.models.Author;
import org.example.models.Book;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiServer extends Remote {

    boolean insertAuthor(Author author) throws RemoteException;

    boolean deleteAuthor(Long id) throws RemoteException;

    boolean insertBook(Book book) throws RemoteException;

    boolean deleteBook(Long id) throws RemoteException;

    boolean updateBook(Book book) throws RemoteException;

    boolean moveToAnotherAuthor(Long bookId, Long newAuthorId) throws RemoteException;

    List<Book> findBooksByAuthorName(String authorName) throws RemoteException;

    List<Author> findAllAuthors() throws RemoteException;
}