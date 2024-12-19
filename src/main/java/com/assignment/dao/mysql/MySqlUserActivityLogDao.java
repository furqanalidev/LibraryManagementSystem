package com.assignment.dao.mysql;

import com.assignment.dao.UserActivityLogDao;
import com.assignment.data.UserActivityLog;
import com.assignment.data.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the UserActivityLogDao interface.
 * Handles database operations for user activity log records.
 *
 * @author MeHeR ALi
 */
public class MySqlUserActivityLogDao implements UserActivityLogDao {

    private static final String SELECT_BY_ID = "SELECT * FROM UserActivityLog WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM UserActivityLog";
    private static final String INSERT = "INSERT INTO UserActivityLog (userId, action, referenceId, referenceType, date, details) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE UserActivityLog SET userId = ?, action = ?, referenceId = ?, referenceType = ?, date = ?, details = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM UserActivityLog WHERE id = ?";
    private static final String SELECT_BY_USER = "SELECT * FROM UserActivityLog WHERE userId = ?";
    private static final String SELECT_BY_REFERENCE_TYPE = "SELECT * FROM UserActivityLog WHERE referenceType = ?";
    private static final String SELECT_BY_DATE_RANGE = "SELECT * FROM UserActivityLog WHERE userId = ? AND date BETWEEN ? AND ?";
    private static final String SELECT_BY_ACTION = "SELECT * FROM UserActivityLog WHERE userId = ? AND action = ?";

    private final Connection connection;

    public MySqlUserActivityLogDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<UserActivityLog> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractUserActivityLogFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<UserActivityLog> findAll() throws SQLException {
        List<UserActivityLog> logs = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                logs.add(extractUserActivityLogFromResultSet(rs));
            }
        }
        return logs;
    }

    @Override
    public UserActivityLog save(UserActivityLog log) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setUserActivityLogParameters(stmt, log);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                log.setId(generatedKeys.getInt(1));
            }
            return log;
        }
    }

    @Override
    public boolean update(UserActivityLog log) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setUserActivityLogParameters(stmt, log);
            stmt.setInt(7, log.getId());
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
    public List<UserActivityLog> findByUserId(int userId) throws SQLException {
        List<UserActivityLog> logs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_USER)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(extractUserActivityLogFromResultSet(rs));
            }
        }
        return logs;
    }

    @Override
    public List<UserActivityLog> findByAction(UserActivityLog.Action action) throws SQLException {
        List<UserActivityLog> logs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ACTION)) {
            stmt.setString(1, action.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(extractUserActivityLogFromResultSet(rs));
            }
        }
        return logs;
    }

    @Override
    public List<UserActivityLog> findByReferenceType(UserActivityLog.ReferenceType referenceType) throws SQLException {
        List<UserActivityLog> logs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_REFERENCE_TYPE)) {
            stmt.setString(1, referenceType.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(extractUserActivityLogFromResultSet(rs));
            }
        }
        return logs;
    }

    @Override
    public List<UserActivityLog> findByDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate)
            throws SQLException {
        List<UserActivityLog> logs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_DATE_RANGE)) {
            stmt.setInt(1, userId);
            stmt.setTimestamp(2, Timestamp.valueOf(startDate));
            stmt.setTimestamp(3, Timestamp.valueOf(endDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(extractUserActivityLogFromResultSet(rs));
            }
        }
        return logs;
    }

    @Override
    public List<UserActivityLog> findByAction(int userId, UserActivityLog.Action action) throws SQLException {
        List<UserActivityLog> logs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ACTION)) {
            stmt.setInt(1, userId);
            stmt.setString(2, action.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(extractUserActivityLogFromResultSet(rs));
            }
        }
        return logs;
    }

    private UserActivityLog extractUserActivityLogFromResultSet(ResultSet rs) throws SQLException {
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
                        userRs.getTimestamp("created_at") != null ? userRs.getTimestamp("created_at").toLocalDateTime()
                                : null);

                return new UserActivityLog(
                        rs.getInt("id"),
                        user,
                        UserActivityLog.Action.valueOf(rs.getString("action")),
                        rs.getInt("referenceId"),
                        UserActivityLog.ReferenceType.valueOf(rs.getString("referenceType")),
                        rs.getTimestamp("date").toLocalDateTime(),
                        rs.getString("details"));
            }
        }
        throw new SQLException("Required data not found for UserActivityLog record");
    }

    private void setUserActivityLogParameters(PreparedStatement stmt, UserActivityLog log) throws SQLException {
        stmt.setInt(1, log.getUser().getId());
        stmt.setString(2, log.getAction().name());
        stmt.setInt(3, log.getReferenceId());
        stmt.setString(4, log.getReferenceType().name());
        stmt.setTimestamp(5, Timestamp.valueOf(log.getDate()));
        stmt.setString(6, log.getDetails());
    }
}
