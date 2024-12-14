package com.assignment.dao.mysql;

import com.assignment.dao.GenreDao;
import com.assignment.data.Genre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the GenreDao interface.
 *
 * @author MeHeR ALi
 */
public class MySqlGenreDao implements GenreDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM Genre WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM Genre";
    private static final String INSERT = 
        "INSERT INTO Genre (name) VALUES (?)";
    private static final String UPDATE = 
        "UPDATE Genre SET name = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM Genre WHERE id = ?";
    private static final String SELECT_BY_NAME = 
        "SELECT * FROM Genre WHERE name = ?";

    private final Connection connection;

    public MySqlGenreDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Genre> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractGenreFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> findAll() throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                genres.add(extractGenreFromResultSet(rs));
            }
        }
        return genres;
    }

    @Override
    public Genre save(Genre genre) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, genre.getName());
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                genre.setId(generatedKeys.getInt(1));
            }
            return genre;
        }
    }

    @Override
    public boolean update(Genre genre) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, genre.getName());
            stmt.setInt(2, genre.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Genre> findByName(String name) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_NAME)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractGenreFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    private Genre extractGenreFromResultSet(ResultSet rs) throws SQLException {
        return new Genre(
            rs.getInt("id"),
            rs.getString("name")
        );
    }
}
