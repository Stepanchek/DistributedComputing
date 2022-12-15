package com.example.servlet;

import com.example.dao.BookDAO;
import com.example.models.Book;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BookServlet extends HttpServlet {
    private transient BookDAO bookDao;

    @Override
    public void init() throws ServletException {
        this.bookDao = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processQuery(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processQuery(request, response);
    }

    private void processQuery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getServletPath();
        String query = url.substring(7);

        switch (query) {
            case "New" -> showNewForm(request, response);

            case "Insert" -> insertPlayer(request, response);

            case "Delete" -> deletePlayer(request, response);

            case "Edit" -> showEditForm(request, response);

            case "Update" -> updatePlayer(request, response);

            case "List" -> listPlayers(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertPlayer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long authorId = Long.parseLong(request.getParameter("authorId"));
        String name = request.getParameter("name");
        BigDecimal price = new BigDecimal(request.getParameter("price"));

        Book book = new Book();
        book.setAuthorId(authorId);
        book.setName(name);
        book.setPrice(price);
        bookDao.insert(book);
        response.sendRedirect("BookList");
    }

    private void deletePlayer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("bookId"));
        bookDao.deleteById(id);
        response.sendRedirect("BookList");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("bookId"));
        Optional<Book> bookOptional = bookDao.findById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
        request.setAttribute("book", bookOptional.orElse(null));
        dispatcher.forward(request, response);
    }

    private void updatePlayer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("bookId"));
        Long authorId = Long.parseLong(request.getParameter("authorId"));
        String name = request.getParameter("name");
        BigDecimal price = new BigDecimal(request.getParameter("price"));

        Book book = new Book(id, authorId, name, price);
        bookDao.update(book);
        response.sendRedirect("BookList");
    }

    private void listPlayers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookDao.findAll();
        request.setAttribute("listBook", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
        dispatcher.forward(request, response);
    }
}