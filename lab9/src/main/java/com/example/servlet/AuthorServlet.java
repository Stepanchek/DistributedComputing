package com.example.servlet;


import com.example.dao.AuthorDAO;
import com.example.models.Author;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AuthorServlet extends HttpServlet {
    private transient AuthorDAO authorDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.authorDao = new AuthorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processQuery(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processQuery(request, response);
    }

    private void processQuery(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getServletPath();
        String query = url.substring(5);

        switch (query) {
            case "New" -> showNewForm(request, response);

            case "Insert" -> insertTeam(request, response);

            case "Delete" -> deleteTeam(request, response);

            case "Edit" -> showEditForm(request, response);

            case "Update" ->  updateTeam(request, response);

            case "List" -> listTeams(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("AuthorForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertTeam(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");

        Author author = new Author();
        author.setName(name);
        authorDao.insert(author);
        response.sendRedirect("AuthorList");
    }

    private void deleteTeam(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        authorDao.deleteById(id);
        response.sendRedirect("AuthorList");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("authorId"));
        Optional<Author> teamOptional = authorDao.findById(id);
        Author author = teamOptional.orElse(null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("AuthorForm.jsp");
        request.setAttribute("Author", author);
        dispatcher.forward(request, response);
    }

    private void updateTeam(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("authorId"));
        String name = request.getParameter("name");

        Author author = new Author(id, name);
        authorDao.update(author);
        response.sendRedirect("AuthorList");
    }

    private void listTeams(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Author> authors = authorDao.findAll();
        request.setAttribute("listAuthor", authors);
        RequestDispatcher dispatcher = request.getRequestDispatcher("AuthorList.jsp");
        dispatcher.forward(request, response);
    }
}