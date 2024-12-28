package com.assignment.dao.mysql;

import com.assignment.dao.StaffDao;
import com.assignment.data.Staff;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the StaffDao interface.
 *
 * @author MeHeR ALi
 */
public class MySqlStaffDao implements StaffDao {

    private static final String SELECT_BY_ID = "SELECT * FROM Staff WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM Staff";
    private static final String INSERT = "INSERT INTO Staff (username, firstName, lastName, cnic, address, contact, email, occupation) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Staff SET username = ?, firstName = ?, lastName = ?, cnic = ?, address = ?, contact = ?, email = ?, occupation = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Staff WHERE id = ?";
    private static final String SELECT_BY_EMAIL = "SELECT * FROM Staff WHERE email = ?";
    private static final String SELECT_BY_CNIC = "SELECT * FROM Staff WHERE cnic = ?";
    private static final String SELECT_BY_CONTACT = "SELECT * FROM Staff WHERE contact = ?";
    private static final String SELECT_BY_OCCUPATION = "SELECT * FROM Staff WHERE occupation = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM Staff WHERE username = ?";

    private final Connection connection;

    public MySqlStaffDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Staff> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractStaffFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Staff> findAll() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                staffList.add(extractStaffFromResultSet(rs));
            }
        }
        return staffList;
    }

    @Override
    public List<Staff> findByOccupation(Staff.Occupation occupation) throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_OCCUPATION)) {
            stmt.setString(1, occupation.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                staffList.add(extractStaffFromResultSet(rs));
            }
        }
        return staffList;
    }

    @Override
    public Staff save(Staff staff) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setStaffParameters(stmt, staff);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                staff.setId(generatedKeys.getInt(1));
            }
            return staff;
        }
    }

    @Override
    public boolean update(Staff staff) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setStaffParameters(stmt, staff);
            stmt.setInt(9, staff.getId());
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
    public Optional<Staff> findByEmail(String email) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_EMAIL)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractStaffFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Staff> findByCnic(long cnic) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_CNIC)) {
            stmt.setLong(1, cnic);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractStaffFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Staff> findByContact(String contact) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_CONTACT)) {
            stmt.setString(1, contact);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractStaffFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Staff> findByUsername(String username) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_USERNAME)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractStaffFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    private Staff extractStaffFromResultSet(ResultSet rs) throws SQLException {
        return new Staff(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getLong("cnic"),
                rs.getString("address"),
                rs.getString("contact"),
                rs.getString("email"),
                Staff.Occupation.valueOf(rs.getString("occupation")),
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
    }

    private void setStaffParameters(PreparedStatement stmt, Staff staff) throws SQLException {
        stmt.setString(1, staff.getUsername());
        stmt.setString(2, staff.getFirstName());
        stmt.setString(3, staff.getLastName());
        stmt.setLong(4, staff.getCnic());
        stmt.setString(5, staff.getAddress());
        stmt.setString(6, staff.getContact());
        stmt.setString(7, staff.getEmail());
        stmt.setString(8, staff.getOccupation().name());
    }
}
