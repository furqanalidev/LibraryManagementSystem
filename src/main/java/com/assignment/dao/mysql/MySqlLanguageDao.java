package com.assignment.dao.mysql;

import com.assignment.dao.LanguageDao;
import com.assignment.data.Language;
import com.assignment.service.ServiceException;
import com.assignment.service.impl.DatabaseConnectionServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the LanguageDao interface.
 *
 * @author MeHeR ALi
 */
public class MySqlLanguageDao implements LanguageDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM Language WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM Language";
    private static final String INSERT = 
        "INSERT INTO Language (name) VALUES (?)";
    private static final String UPDATE = 
        "UPDATE Language SET name = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM Language WHERE id = ?";
    private static final String SELECT_BY_NAME = 
        "SELECT * FROM Language WHERE name = ?";

    private Connection connection;

    

    public MySqlLanguageDao(Connection connection) {
        this.connection = connection;
    }

    public MySqlLanguageDao() {
        this.connection = null;
        try {
            Connection conn = DatabaseConnectionServiceImpl.newConnection();
            this.connection = conn;
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Language> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractLanguageFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Language> findAll() throws SQLException {
        List<Language> languages = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                languages.add(extractLanguageFromResultSet(rs));
            }
        }
        return languages;
    }

    @Override
    public Language save(Language language) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, language.getName());
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                language.setId(generatedKeys.getInt(1));
            }
            return language;
        }
    }

    @Override
    public boolean update(Language language) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, language.getName());
            stmt.setInt(2, language.getId());
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
    public Optional<Language> findByName(String name) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_NAME)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractLanguageFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    private Language extractLanguageFromResultSet(ResultSet rs) throws SQLException {
        return new Language(
            rs.getInt("id"),
            rs.getString("name")
        );
    }
}
