package com.assignment.dao.mysql;

import com.assignment.dao.UserBookBorrowDao;
import com.assignment.data.UserBookBorrow;
import com.assignment.data.User;
import com.assignment.data.BookBorrow;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the UserBookBorrowDao interface.
 * Handles database operations for user book borrowing records.
 *
 * @author MeHeR ALi
 */
public class MySqlUserBookBorrowDao implements UserBookBorrowDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM UserBookBorrow WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM UserBookBorrow";
    private static final String INSERT = 
        "INSERT INTO UserBookBorrow (userId, bookBorrowId) VALUES (?, ?)";
    private static final String UPDATE = 
        "UPDATE UserBookBorrow SET userId = ?, bookBorrowId = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM UserBookBorrow WHERE id = ?";
    private static final String SELECT_BY_USER = 
        "SELECT * FROM UserBookBorrow WHERE userId = ?";
    private static final String SELECT_BY_BOOK_BORROW = 
        "SELECT * FROM UserBookBorrow WHERE bookBorrowId = ?";

    private final Connection connection;

    /**
     * Constructs a new MySqlUserBookBorrowDao with the specified database connection.
     *
     * @param connection the database connection to use
     */
    public MySqlUserBookBorrowDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<UserBookBorrow> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractUserBookBorrowFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<UserBookBorrow> findAll() throws SQLException {
        List<UserBookBorrow> borrows = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                borrows.add(extractUserBookBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public UserBookBorrow save(UserBookBorrow userBookBorrow) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setUserBookBorrowParameters(stmt, userBookBorrow);
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                userBookBorrow.setId(generatedKeys.getInt(1));
            }
            return userBookBorrow;
        }
    }

    @Override
    public boolean update(UserBookBorrow userBookBorrow) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setUserBookBorrowParameters(stmt, userBookBorrow);
            stmt.setInt(3, userBookBorrow.getId());
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
    public List<UserBookBorrow> findByUserId(int userId) throws SQLException {
        List<UserBookBorrow> borrows = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_USER)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrows.add(extractUserBookBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public List<UserBookBorrow> findByBookBorrowId(int bookBorrowId) throws SQLException {
        List<UserBookBorrow> borrows = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_BOOK_BORROW)) {
            stmt.setInt(1, bookBorrowId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrows.add(extractUserBookBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    private UserBookBorrow extractUserBookBorrowFromResultSet(ResultSet rs) throws SQLException {
        // Get User details
        String userQuery = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement userStmt = connection.prepareStatement(userQuery)) {
            userStmt.setInt(1, rs.getInt("userId"));
            ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) {
                User user = new User(
                    userRs.getInt("id"),
                    userRs.getString("username"),
                    userRs.getString("firstName"),
                    userRs.getString("lastName"),
                    userRs.getLong("cnic"),
                    userRs.getString("address"),
                    userRs.getString("contact"),
                    userRs.getString("email"),
                    userRs.getInt("borrowings"),
                    userRs.getInt("bookBorrowLimit"),
                    userRs.getInt("magazineBorrowLimit"),
                    userRs.getTimestamp("created_at") != null ? 
                        userRs.getTimestamp("created_at").toLocalDateTime() : null
                );

                // Get BookBorrow using MySqlBookBorrowDao
                MySqlBookBorrowDao bookBorrowDao = new MySqlBookBorrowDao(connection);
                Optional<BookBorrow> bookBorrow = bookBorrowDao.findById(rs.getInt("bookBorrowId"));
                
                if (bookBorrow.isPresent()) {
                    return new UserBookBorrow(rs.getInt("id"), user, bookBorrow.get());
                }
            }
        }
        throw new SQLException("Required data not found for UserBookBorrow record");
    }
    private void setUserBookBorrowParameters(PreparedStatement stmt, UserBookBorrow userBookBorrow) throws SQLException {
        stmt.setInt(1, userBookBorrow.getUser().getId());
        stmt.setInt(2, userBookBorrow.getBorrow().getId());
    }
}
