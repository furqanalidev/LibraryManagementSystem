package com.assignment.dao.mysql;

import com.assignment.dao.GenreDao;
import com.assignment.dao.LanguageDao;
import com.assignment.dao.MagazineDao;
import com.assignment.data.Magazine;
import com.assignment.service.GenreService;
import com.assignment.service.LanguageService;
import com.assignment.service.impl.DatabaseConnectionServiceImpl;
import com.assignment.service.impl.GenreServiceImpl;
import com.assignment.service.impl.LanguageServiceImpl;
import com.assignment.data.Language;
import com.assignment.data.Genre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

/**
 * MySQL implementation of the MagazineDao interface.
 *
 * @author MeHeR ALi
 */
public class MySqlMagazineDao implements MagazineDao {

    private static final String SELECT_BY_ID = "SELECT * FROM Magazine WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM Magazine";
    private static final String INSERT = "INSERT INTO Magazine (title, availableCopies, isBorrowable, genreId, languageId) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Magazine SET title = ?, availableCopies = ?, isBorrowable = ?, genreId = ?, languageId = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Magazine WHERE id = ?";
    private static final String UPDATE_COPIES = "UPDATE Magazine SET availableCopies = ? WHERE id = ?";
    private static final String SELECT_BY_TITLE = "SELECT * FROM Magazine WHERE title LIKE ?";
    private static final String SELECT_BY_LANGUAGE = "SELECT * FROM Magazine WHERE languageId = ?";
    private static final String SELECT_AVAILABLE = "SELECT * FROM Magazine WHERE availableCopies > 0";
    private static final String SELECT_BY_GENRE = "SELECT * FROM Magazine WHERE genreId = ?";

    private final Connection connection;

    public MySqlMagazineDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Magazine> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractMagazineFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Magazine> findAll() throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                magazines.add(extractMagazineFromResultSet(rs));
            }
        }
        return magazines;
    }

    @Override
    public Magazine save(Magazine magazine) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setMagazineParameters(stmt, magazine);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                magazine.setId(generatedKeys.getInt(1));
            }
            return magazine;
        }
    }

    @Override
    public boolean update(Magazine magazine) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setMagazineParameters(stmt, magazine);
            stmt.setInt(6, magazine.getId());
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
    public List<Magazine> findByGenre(int genreId) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_GENRE)) {
            stmt.setInt(1, genreId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazineFromResultSet(rs));
            }
        }
        return magazines;
    }

    @Override
    public List<Magazine> findByTitle(String title) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_TITLE)) {
            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazineFromResultSet(rs));
            }
        }
        return magazines;
    }

    @Override
    public List<Magazine> findByLanguage(Language language) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_LANGUAGE)) {
            stmt.setInt(1, language.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazineFromResultSet(rs));
            }
        }
        return magazines;
    }

    @Override
    public List<Magazine> findAvailable() throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_AVAILABLE);
            while (rs.next()) {
                magazines.add(extractMagazineFromResultSet(rs));
            }
        }
        return magazines;
    }

    @Override
    public List<Magazine> findByGenre(Genre genre) throws SQLException {
        List<Magazine> magazines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_GENRE)) {
            stmt.setInt(1, genre.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                magazines.add(extractMagazineFromResultSet(rs));
            }
        }
        return magazines;
    }

    @Override
    public boolean updateAvailableCopies(int magazineId, int count) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_COPIES)) {
            stmt.setInt(1, count);
            stmt.setInt(2, magazineId);
            return stmt.executeUpdate() > 0;
        }
    }

    private Magazine extractMagazineFromResultSet(ResultSet rs) throws SQLException {
        try {
            LanguageDao languageDao = new MySqlLanguageDao();
            LanguageService languageService = new LanguageServiceImpl(languageDao);
            Language language = languageService.findById(rs.getInt("languageId"));
            GenreDao genreDao = new MySqlGenreDao(DatabaseConnectionServiceImpl.newConnection());
            GenreService genreService = new GenreServiceImpl(genreDao);
            Genre genre = genreService.findById(rs.getInt("genreId"));
            return new Magazine(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getInt("availableCopies"),
                rs.getBoolean("isBorrowable"),
                genre,
                language,
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
                rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error while extracting magazine from Database!");
            e.printStackTrace();
        }
        return null;
    }

    private void setMagazineParameters(PreparedStatement stmt, Magazine magazine) throws SQLException {
        stmt.setString(1, magazine.getTitle());
        stmt.setInt(2, magazine.getAvailableCopies());
        stmt.setBoolean(3, magazine.isBorrowable());
        stmt.setInt(4, magazine.getGenre().getId());
        stmt.setInt(5, magazine.getLanguage().getId());
    }
}
