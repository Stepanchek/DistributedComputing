package org.example.DAO;

import org.example.Connector.Connector;
import org.example.Models.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDAO {
    public Optional<Author> findById(Long id) {
        final String sql = "SELECT * FROM teams WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            Optional<Author> result = Optional.empty();

            try (ResultSet rs = statement.executeQuery();) {
                if (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getLong("id"));
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
        final String sql = "SELECT * FROM teams";

        try (Connection connection = Connector.getConnection();
             Statement statement = connection.createStatement()) {

            List<Author> result = new ArrayList<>();

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    Author author = new Author();
                    author.setId(rs.getLong("id"));
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
        final String sql = "UPDATE teams SET name = ?, country = ? WHERE id = ?";

        try (Connection connection = Connector.getConnection();
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
        final String sql = "DELETE FROM teams WHERE id = ?";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int deletedRecords = statement.executeUpdate();

            return deletedRecords > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean insert(Author toInsert) {
        final String sql = "INSERT INTO teams(name, country) VALUES(?, ?)";

        try (Connection connection = Connector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, toInsert.getName());
            int insertedCount = statement.executeUpdate();

            return insertedCount > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
