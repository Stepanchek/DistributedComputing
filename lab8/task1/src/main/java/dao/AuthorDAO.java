package dao;

import connection.SingletonConnector;
import models.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDAO {
    public Optional<Author> findById(Long id) {
        final String sql = "SELECT * FROM authors WHERE authorId = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            Optional<Author> result = Optional.empty();

            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getLong("authorId"));
                    author.setName(rs.getString("name"));
                    result = Optional.of(author);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Author> findAll() {
        final String sql = "SELECT * FROM authors";

        try (Connection connection = SingletonConnector.getConnection();
             Statement statement = connection.createStatement()) {

            List<Author> result = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getLong("authorId"));
                    author.setName(rs.getString("name"));
                    result.add(author);
                }
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Author updated) {
        final String sql = "UPDATE authors SET name = ? WHERE authorId = ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, updated.getName());
            statement.setLong(2, updated.getId());
            int updatedRecords = statement.executeUpdate();

            return updatedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(Long id) {
        final String sql = "DELETE FROM authors WHERE authorId= ?";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int deletedRecords = statement.executeUpdate();

            return deletedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Author toInsert) {
        final String sql = "INSERT INTO authors(name) VALUES(?)";

        try (Connection connection = SingletonConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, toInsert.getName());
            int insertedCount = statement.executeUpdate();

            return insertedCount > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
