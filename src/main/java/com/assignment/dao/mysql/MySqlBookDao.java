package com.assignment.dao.mysql;

import com.assignment.dao.BookDao;
import com.assignment.data.Book;
import com.assignment.data.Language;
import com.assignment.data.Genre;
import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the BookDao interface.
 *
 * @author MeHeR ALi
 */
public class MySqlBookDao implements BookDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM Book WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM Book";
    private static final String INSERT = 
        "INSERT INTO Book (isbn, title, author, publisher, language_id, year, availableCopies, genre_id, isBorrowable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = 
        "UPDATE Book SET isbn = ?, title = ?, author = ?, publisher = ?, language_id = ?, year = ?, availableCopies = ?, genre_id = ?, isBorrowable = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM Book WHERE id = ?";
    private static final String SELECT_BY_ISBN = 
        "SELECT * FROM Book WHERE isbn = ?";
    private static final String SELECT_BY_AUTHOR = 
        "SELECT * FROM Book WHERE author = ?";
    private static final String SELECT_BY_GENRE = 
        "SELECT * FROM Book WHERE genre_id = ?";
    private static final String UPDATE_COPIES = 
        "UPDATE Book SET availableCopies = ? WHERE id = ?";

    private final Connection connection;

    public MySqlBookDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Book> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractBookFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        }
        return books;
    }

    @Override
    public Book save(Book book) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setBookParameters(stmt, book);
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                book.setId(generatedKeys.getInt(1));
            }
            return book;
        }
    }

    @Override
    public boolean update(Book book) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setBookParameters(stmt, book);
            stmt.setInt(10, book.getId());
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
    public Optional<Book> findByIsbn(String isbn) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ISBN)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractBookFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findByAuthor(String author) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_AUTHOR)) {
            stmt.setString(1, author);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        }
        return books;
    }

    @Override
    public List<Book> findByGenre(int genreId) throws SQLException {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_GENRE)) {
            stmt.setInt(1, genreId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(extractBookFromResultSet(rs));
            }
        }
        return books;
    }

    @Override
    public boolean updateAvailableCopies(int bookId, int count) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_COPIES)) {
            stmt.setInt(1, count);
            stmt.setInt(2, bookId);
            return stmt.executeUpdate() > 0;
        }
    }

    private Book extractBookFromResultSet(ResultSet rs) throws SQLException {
        Language language = new Language(rs.getInt("language_id"), null); // We'll need to fetch actual language details
        Genre genre = new Genre(rs.getInt("genre_id"), null); // We'll need to fetch actual genre details
        
        return new Book(
            rs.getInt("id"),
            rs.getString("isbn"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("publisher"),
            language,
            Year.of(rs.getInt("year")),
            rs.getInt("availableCopies"),
            genre,
            rs.getBoolean("isBorrowable"),
            rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null,
            rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
        );
    }

    private void setBookParameters(PreparedStatement stmt, Book book) throws SQLException {
        stmt.setString(1, book.getIsbn());
        stmt.setString(2, book.getTitle());
        stmt.setString(3, book.getAuthor());
        stmt.setString(4, book.getPublisher());
        stmt.setInt(5, book.getLanguage().getId());
        stmt.setInt(6, book.getYear().getValue());
        stmt.setInt(7, book.getAvailableCopies());
        stmt.setInt(8, book.getGenre().getId());
        stmt.setBoolean(9, book.isBorrowable());
    }}