package com.assignment.dao.mysql;

import com.assignment.dao.MagazineBorrowDao;
import com.assignment.data.MagazineBorrow;
import com.assignment.data.Borrow;
import com.assignment.data.Staff;
import com.assignment.data.Magazine;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the MagazineBorrowDao interface.
 * Handles database operations for magazine borrowing records.
 *
 * @author MeHeR ALi
 */
public class MySqlMagazineBorrowDao implements MagazineBorrowDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM MagazineBorrow WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM MagazineBorrow";
    private static final String INSERT = 
        "INSERT INTO MagazineBorrow (magazineId, date, status, staffId) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = 
        "UPDATE MagazineBorrow SET magazineId = ?, date = ?, status = ?, staffId = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM MagazineBorrow WHERE id = ?";
    private static final String SELECT_BY_STATUS = 
        "SELECT * FROM MagazineBorrow WHERE status = ?";
    private static final String SELECT_BY_MAGAZINE = 
        "SELECT * FROM MagazineBorrow WHERE magazineId = ?";
    private static final String UPDATE_STATUS = 
        "UPDATE MagazineBorrow SET status = ? WHERE id = ?";

    private final Connection connection;

    public MySqlMagazineBorrowDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<MagazineBorrow> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractMagazineBorrowFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<MagazineBorrow> findAll() throws SQLException {
        List<MagazineBorrow> borrows = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                borrows.add(extractMagazineBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public MagazineBorrow save(MagazineBorrow borrow) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setMagazineBorrowParameters(stmt, borrow);
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                borrow.setId(generatedKeys.getInt(1));
            }
            return borrow;
        }
    }

    @Override
    public boolean update(MagazineBorrow borrow) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setMagazineBorrowParameters(stmt, borrow);
            stmt.setInt(5, borrow.getId());
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
    public List<MagazineBorrow> findByStatus(Borrow.Status status) throws SQLException {
        List<MagazineBorrow> borrows = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_STATUS)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrows.add(extractMagazineBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public List<MagazineBorrow> findByMagazineId(int magazineId) throws SQLException {
        List<MagazineBorrow> borrows = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_MAGAZINE)) {
            stmt.setInt(1, magazineId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrows.add(extractMagazineBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public boolean updateStatus(int borrowId, Borrow.Status status) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_STATUS)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, borrowId);
            return stmt.executeUpdate() > 0;
        }
    }

    private MagazineBorrow extractMagazineBorrowFromResultSet(ResultSet rs) throws SQLException {
        // Get Staff details
        String staffQuery = "SELECT * FROM Staff WHERE id = ?";
        try (PreparedStatement staffStmt = connection.prepareStatement(staffQuery)) {
            staffStmt.setInt(1, rs.getInt("staffId"));
            ResultSet staffRs = staffStmt.executeQuery();
            if (staffRs.next()) {
                Staff staff = new Staff(
                    staffRs.getInt("id"),
                    staffRs.getString("username"),
                    staffRs.getString("firstName"),
                    staffRs.getString("lastName"),
                    staffRs.getLong("cnic"),
                    staffRs.getString("address"),
                    staffRs.getString("contact"),
                    staffRs.getString("email"),
                    Staff.Occupation.valueOf(staffRs.getString("occupation")),
                    staffRs.getTimestamp("created_at") != null ? 
                        staffRs.getTimestamp("created_at").toLocalDateTime() : null
                );

                // Get Magazine details
                String magazineQuery = "SELECT * FROM Magazine WHERE id = ?";
                try (PreparedStatement magazineStmt = connection.prepareStatement(magazineQuery)) {
                    magazineStmt.setInt(1, rs.getInt("magazineId"));
                    ResultSet magazineRs = magazineStmt.executeQuery();
                    if (magazineRs.next()) {
                        Magazine magazine = new Magazine(
                            magazineRs.getInt("id"),
                            magazineRs.getString("title"),
                            magazineRs.getInt("availableCopies"),
                            magazineRs.getBoolean("isBorrowable"),
                            new Genre(magazineRs.getInt("genreId"), null),
                            new Language(magazineRs.getInt("languageId"), null),
                            magazineRs.getTimestamp("created_at") != null ? 
                                magazineRs.getTimestamp("created_at").toLocalDateTime() : null,
                            magazineRs.getTimestamp("updated_at") != null ? 
                                magazineRs.getTimestamp("updated_at").toLocalDateTime() : null
                        );

                        return new MagazineBorrow(
                            rs.getInt("id"),
                            rs.getTimestamp("date").toLocalDateTime(),
                            Borrow.Status.valueOf(rs.getString("status")),
                            magazine,
                            staff,
                            rs.getTimestamp("created_at") != null ? 
                                rs.getTimestamp("created_at").toLocalDateTime() : null,
                            rs.getTimestamp("updated_at") != null ? 
                                rs.getTimestamp("updated_at").toLocalDateTime() : null
                        );
                    }
                }
            }
        }
        throw new SQLException("Required data not found for MagazineBorrow record");
    }

    private void setMagazineBorrowParameters(PreparedStatement stmt, MagazineBorrow borrow) throws SQLException {
        stmt.setInt(1, borrow.getMagazine().getId());
        stmt.setTimestamp(2, Timestamp.valueOf(borrow.getDate()));
        stmt.setString(3, borrow.getStatus().name());
        stmt.setInt(4, borrow.getStaff().getId());
    }
}
