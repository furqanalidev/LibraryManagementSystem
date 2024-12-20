package com.assignment.dao;

import com.assignment.data.Staff;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Staff entity operations.
 *
 * @author MeHeR ALi
 */
public interface StaffDao extends Dao<Staff> {
    
    /**
     * Finds a staff member by their email.
     *
     * @param email the email to search for
     * @return Optional containing the found staff member or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<Staff> findByEmail(String email) throws SQLException;
    
    /**
     * Finds a staff member by their CNIC.
     *
     * @param cnic the CNIC to search for
     * @return Optional containing the found staff member or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<Staff> findByCnic(long cnic) throws SQLException;
    
    /**
     * Finds a staff member by their contact number.
     *
     * @param contact the contact number to search for
     * @return Optional containing the found staff member or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<Staff> findByContact(String contact) throws SQLException;
    List<Staff> findByOccupation(Staff.Occupation occupation) throws SQLException;
    Optional<Staff> findByUsername(String username) throws SQLException;
    List<Staff> findAll() throws SQLException;
}

