package dao;

import connection.SingletonConnector;
import models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAO {
    public Optional<Book> findById(Long id) {
        final String sql = "SELECT * FROM books WHERE bookId = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            Optional<Book> result = Optional.empty();

            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getLong("bookId"));
                    book.setAuthorId(rs.getLong("authorId"));
                    book.setName(rs.getString("name"));
                    book.setPrice(rs.getBigDecimal("price"));
                    result = Optional.of(book);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findAll() {
        final String sql = "SELECT * FROM books";

        try (Connection connection = SingletonConnector.getConnection();
             Statement statement = connection.createStatement()) {

            List<Book> result = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getLong("bookId"));
                    book.setAuthorId(rs.getLong("authorId"));
                    book.setName(rs.getString("name"));
                    book.setPrice(rs.getBigDecimal("price"));
                    result.add(book);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Book updated) {
        final String sql = "UPDATE books SET authorId = ?, name = ?, price = ? WHERE bookId = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, updated.getAuthorId());
            statement.setString(2, updated.getName());
            statement.setBigDecimal(3, updated.getPrice());
            statement.setLong(4, updated.getId());
            int updatedRecords = statement.executeUpdate();

            return updatedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(Long id) {
        final String sql = "DELETE FROM books WHERE bookId = ?";
        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int deletedRecords = statement.executeUpdate();
            return deletedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Book toInsert) {
        final String sql = "INSERT INTO sus.books(authorId, name, price) VALUES(?, ?, ?)";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, toInsert.getAuthorId());
            statement.setString(2, toInsert.getName());
            statement.setBigDecimal(3, toInsert.getPrice());
            int insertedCount = statement.executeUpdate();

            return insertedCount > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findByAuthorName(String teamName) {
        final String sql = """
            SELECT * FROM books WHERE authorId = (
                SELECT authorId FROM authors WHERE authors.name = ?
            )
        """;

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, teamName);
            List<Book> result;

            try (ResultSet rs = statement.executeQuery()) {
                result = getBooksFromResultSet(rs);
            }

            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean moveToAnotherAuthor(Long playerId, Long newTeamId) {
        Optional<Book> bookOptional = findById(playerId);

        if (bookOptional.isEmpty()) {
            return false;
        }

        Book book = bookOptional.get();
        book.setAuthorId(newTeamId);
        update(book);

        return true;
    }

    private List<Book> getBooksFromResultSet(ResultSet rs) throws SQLException {
        List<Book> result = new ArrayList<>();

        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getLong("bookId"));
            book.setAuthorId(rs.getLong("authorId"));
            book.setName(rs.getString("name"));
            book.setPrice(rs.getBigDecimal("price"));
            result.add(book);
        }

        return result;
    }
}
