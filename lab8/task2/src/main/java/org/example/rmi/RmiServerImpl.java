package org.example.rmi;


import org.example.dao.AuthorDAO;
import org.example.dao.BookDAO;
import org.example.models.Author;
import org.example.models.Book;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RmiServerImpl extends UnicastRemoteObject implements RmiServer {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final transient ReadWriteLock lock;

    public RmiServerImpl() throws RemoteException {
        this.bookDAO = new BookDAO();
        this.authorDAO = new AuthorDAO();
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public boolean insertAuthor(Author author) throws RemoteException {
        try {
            lock.writeLock().lock();
            return authorDAO.insert(author);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean deleteAuthor(Long id) throws RemoteException {
        try {
            lock.writeLock().lock();
            return authorDAO.deleteById(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean insertBook(Book book) throws RemoteException {
        try {
            lock.writeLock().lock();
            return bookDAO.insert(book);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean deleteBook(Long id) throws RemoteException {
        try {
            lock.writeLock().lock();
            return bookDAO.deleteById(id);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean updateBook(Book book) throws RemoteException {
        try {
            lock.writeLock().lock();
            return bookDAO.update(book);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean moveToAnotherAuthor(Long bookId, Long newAuthorId) throws RemoteException {
        try {
            lock.writeLock().lock();
            return bookDAO.moveToAnotherAuthor(bookId, newAuthorId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Book> findBooksByAuthorName(String authorName) throws RemoteException {
        try {
            lock.readLock().lock();
            return bookDAO.findByAuthorName(authorName);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Author> findAllAuthors() throws RemoteException {
        try {
            lock.readLock().lock();
            return authorDAO.findAll();
        } finally {
            lock.readLock().unlock();
        }
    }
}