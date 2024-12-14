package com.assignment.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.assignment.dao.BookFineDao;
import com.assignment.data.BookFine;
import com.assignment.data.Fine;

/**
 * MySQL implementation of the BookFineDao interface.
 * Handles database operations for book-related fines.
 *
 * @author MeHeR ALi
 */
public class MySqlBookFineDao implements BookFineDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM BookFine WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM BookFine";
    private static final String INSERT = 
        "INSERT INTO BookFine (userBookBorrowId, amount, status, reason) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = 
        "UPDATE BookFine SET userBookBorrowId = ?, amount = ?, status = ?, reason = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM BookFine WHERE id = ?";
    private static final String SELECT_BY_STATUS = 
        "SELECT * FROM BookFine WHERE status = ?";
    private static final String SELECT_BY_BORROW = 
        "SELECT * FROM BookFine WHERE userBookBorrowId = ?";
    private static final String UPDATE_STATUS = 
        "UPDATE BookFine SET status = ? WHERE id = ?";

    private final Connection connection;

    /**
     * Constructs a new MySqlBookFineDao with the specified database connection.
     *
     * @param connection the database connection to use
     */
    public MySqlBookFineDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<BookFine> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractBookFineFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<BookFine> findAll() throws SQLException {
        List<BookFine> fines = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                fines.add(extractBookFineFromResultSet(rs));
            }
        }
        return fines;
    }

    @Override
    public BookFine save(BookFine fine) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setBookFineParameters(stmt, fine);
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                fine.setId(generatedKeys.getInt(1));
            }
            return fine;
        }
    }

    @Override
    public boolean update(BookFine fine) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setBookFineParameters(stmt, fine);
            stmt.setInt(5, fine.getId());
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
    public List<BookFine> findByStatus(Fine.FineStatus status) throws SQLException {
        List<BookFine> fines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_STATUS)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fines.add(extractBookFineFromResultSet(rs));
            }
        }
        return fines;
    }

    @Override
    public List<BookFine> findByUserBookBorrowId(int userBookBorrowId) throws SQLException {
        List<BookFine> fines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_BORROW)) {
            stmt.setInt(1, userBookBorrowId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fines.add(extractBookFineFromResultSet(rs));
            }
        }
        return fines;
    }

    @Override
    public boolean updateStatus(int fineId, Fine.FineStatus status) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_STATUS)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, fineId);
            return stmt.executeUpdate() > 0;
        }
    }

    private BookFine extractBookFineFromResultSet(ResultSet rs) throws SQLException {
        return new BookFine(
            rs.getInt("id"),
            rs.getBigDecimal("amount"),
            Fine.FineStatus.valueOf(rs.getString("status")),
            rs.getString("reason"),
            rs.getInt("userBookBorrowId"),
            rs.getTimestamp("created_at") != null ? 
                rs.getTimestamp("created_at").toLocalDateTime() : null
        );
    }

    private void setBookFineParameters(PreparedStatement stmt, BookFine fine) throws SQLException {
        stmt.setInt(1, fine.getUserBookBorrowId());
        stmt.setBigDecimal(2, fine.getAmount());
        stmt.setString(3, fine.getStatus().name());
        stmt.setString(4, fine.getReason());
    }
}
