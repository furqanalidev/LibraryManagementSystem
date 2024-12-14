package com.assignment.dao.mysql;

import com.assignment.dao.UserMagazineBorrowDao;
import com.assignment.data.UserMagazineBorrow;
import com.assignment.data.User;
import com.assignment.data.MagazineBorrow;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the UserMagazineBorrowDao interface.
 * Handles database operations for user magazine borrowing records.
 *
 * @author MeHeR ALi
 */
public class MySqlUserMagazineBorrowDao implements UserMagazineBorrowDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM UserMagazineBorrow WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM UserMagazineBorrow";
    private static final String INSERT = 
        "INSERT INTO UserMagazineBorrow (userId, magazineBorrowId) VALUES (?, ?)";
    private static final String UPDATE = 
        "UPDATE UserMagazineBorrow SET userId = ?, magazineBorrowId = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM UserMagazineBorrow WHERE id = ?";
    private static final String SELECT_BY_USER = 
        "SELECT * FROM UserMagazineBorrow WHERE userId = ?";
    private static final String SELECT_BY_MAGAZINE_BORROW = 
        "SELECT * FROM UserMagazineBorrow WHERE magazineBorrowId = ?";

    private final Connection connection;

    public MySqlUserMagazineBorrowDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<UserMagazineBorrow> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractUserMagazineBorrowFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<UserMagazineBorrow> findAll() throws SQLException {
        List<UserMagazineBorrow> borrows = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                borrows.add(extractUserMagazineBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public UserMagazineBorrow save(UserMagazineBorrow userMagazineBorrow) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setUserMagazineBorrowParameters(stmt, userMagazineBorrow);
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                userMagazineBorrow.setId(generatedKeys.getInt(1));
            }
            return userMagazineBorrow;
        }
    }

    @Override
    public boolean update(UserMagazineBorrow userMagazineBorrow) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setUserMagazineBorrowParameters(stmt, userMagazineBorrow);
            stmt.setInt(3, userMagazineBorrow.getId());
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
    public List<UserMagazineBorrow> findByUserId(int userId) throws SQLException {
        List<UserMagazineBorrow> borrows = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_USER)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrows.add(extractUserMagazineBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public List<UserMagazineBorrow> findByMagazineBorrowId(int magazineBorrowId) throws SQLException {
        List<UserMagazineBorrow> borrows = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_MAGAZINE_BORROW)) {
            stmt.setInt(1, magazineBorrowId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrows.add(extractUserMagazineBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    private UserMagazineBorrow extractUserMagazineBorrowFromResultSet(ResultSet rs) throws SQLException {
        // Get User details
        String userQuery = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement userStmt = connection.prepareStatement(userQuery)) {
            userStmt.setInt(1, rs.getInt("userId"));
            ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) {
                User user = new User(
                    userRs.getInt("id"),
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

                // Get MagazineBorrow using MySqlMagazineBorrowDao
                MySqlMagazineBorrowDao magazineBorrowDao = new MySqlMagazineBorrowDao(connection);
                Optional<MagazineBorrow> magazineBorrow = magazineBorrowDao.findById(rs.getInt("magazineBorrowId"));
                
                if (magazineBorrow.isPresent()) {
                    return new UserMagazineBorrow(rs.getInt("id"), user, magazineBorrow.get());
                }
            }
        }
        throw new SQLException("Required data not found for UserMagazineBorrow record");
    }

    private void setUserMagazineBorrowParameters(PreparedStatement stmt, UserMagazineBorrow userMagazineBorrow) throws SQLException {
        stmt.setInt(1, userMagazineBorrow.getUser().getId());
        stmt.setInt(2, userMagazineBorrow.getBorrow().getId());
    }
}
