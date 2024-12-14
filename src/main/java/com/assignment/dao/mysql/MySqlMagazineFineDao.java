package com.assignment.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.assignment.dao.MagazineFineDao;
import com.assignment.data.Fine;
import com.assignment.data.MagazineFine;

/**
 * MySQL implementation of the MagazineFineDao interface.
 * Handles database operations for magazine-related fines.
 *
 * @author MeHeR ALi
 */
public class MySqlMagazineFineDao implements MagazineFineDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM MagazineFine WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM MagazineFine";
    private static final String INSERT = 
        "INSERT INTO MagazineFine (userMagazineBorrowId, amount, status, reason) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = 
        "UPDATE MagazineFine SET userMagazineBorrowId = ?, amount = ?, status = ?, reason = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM MagazineFine WHERE id = ?";
    private static final String SELECT_BY_STATUS = 
        "SELECT * FROM MagazineFine WHERE status = ?";
    private static final String SELECT_BY_BORROW = 
        "SELECT * FROM MagazineFine WHERE userMagazineBorrowId = ?";
    private static final String UPDATE_STATUS = 
        "UPDATE MagazineFine SET status = ? WHERE id = ?";

    private final Connection connection;

    /**
     * Constructs a new MySqlMagazineFineDao with the specified database connection.
     *
     * @param connection the database connection to use
     */
    public MySqlMagazineFineDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<MagazineFine> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractMagazineFineFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MagazineFine> findAll() throws SQLException {
        List<MagazineFine> fines = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                fines.add(extractMagazineFineFromResultSet(rs));
            }
        }
        return fines;
    }

    @Override
    public MagazineFine save(MagazineFine fine) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setMagazineFineParameters(stmt, fine);
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                fine.setId(generatedKeys.getInt(1));
            }
            return fine;
        }
    }

    @Override
    public boolean update(MagazineFine fine) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setMagazineFineParameters(stmt, fine);
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
    public List<MagazineFine> findByStatus(Fine.FineStatus status) throws SQLException {
        List<MagazineFine> fines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_STATUS)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fines.add(extractMagazineFineFromResultSet(rs));
            }
        }
        return fines;
    }

    @Override
    public List<MagazineFine> findByUserMagazineBorrowId(int userMagazineBorrowId) throws SQLException {
        List<MagazineFine> fines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_BORROW)) {
            stmt.setInt(1, userMagazineBorrowId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fines.add(extractMagazineFineFromResultSet(rs));
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

    private MagazineFine extractMagazineFineFromResultSet(ResultSet rs) throws SQLException {
        return new MagazineFine(
            rs.getInt("id"),
            rs.getBigDecimal("amount"),
            Fine.FineStatus.valueOf(rs.getString("status")),
            rs.getString("reason"),
            rs.getInt("userMagazineBorrowId"),
            rs.getTimestamp("created_at") != null ? 
                rs.getTimestamp("created_at").toLocalDateTime() : null
        );
    }

    private void setMagazineFineParameters(PreparedStatement stmt, MagazineFine fine) throws SQLException {
        stmt.setInt(1, fine.getUserMagazineBorrowId());
        stmt.setBigDecimal(2, fine.getAmount());
        stmt.setString(3, fine.getStatus().name());
        stmt.setString(4, fine.getReason());
    }
}
