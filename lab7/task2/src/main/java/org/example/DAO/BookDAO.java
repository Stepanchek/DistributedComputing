package org.example.DAO;

import org.example.Connector.Connector;
import org.example.Models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDAO {
    public Optional<Book> findById(Long id) {
        final String sql = "SELECT * FROM players WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            Optional<Book> result = Optional.empty();

            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getLong("id"));
                    book.setAuthorId(rs.getLong("team_id"));
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
        final String sql = "SELECT * FROM players";

        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement()) {

            List<Book> result = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getLong("id"));
                    book.setAuthorId(rs.getLong("team_id"));
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
        final String sql = "UPDATE players SET team_id = ?, name = ?, price = ? WHERE id = ?";

        try (Connection connection = Connector.getConnection();
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
        final String sql = "DELETE FROM players WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int deletedRecords = statement.executeUpdate();

            return deletedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Book toInsert) {
        final String sql = "INSERT INTO football.players(team_id, name, price) VALUES(?, ?, ?)";

        try (Connection connection = Connector.getConnection();
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
}
