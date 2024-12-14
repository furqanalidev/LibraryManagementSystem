package com.assignment.dao;

import com.assignment.data.User;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Data Access Object interface for User entity operations.
 *
 * @author MeHeR ALi
 */
public interface UserDao extends Dao<User> {
    
    /**
     * Finds a user by their email.
     *
     * @param email the email to search for
     * @return Optional containing the found user or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<User> findByEmail(String email) throws SQLException;
    
    /**
     * Finds a user by their CNIC.
     *
     * @param cnic the CNIC to search for
     * @return Optional containing the found user or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<User> findByCnic(long cnic) throws SQLException;
    
    /**
     * Finds a user by their contact number.
     *
     * @param contact the contact number to search for
     * @return Optional containing the found user or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<User> findByContact(String contact) throws SQLException;
    
    /**
     * Updates the borrowing count for a user.
     *
     * @param userId the ID of the user
     * @param borrowings the new borrowing count
     * @return true if update was successful
     * @throws SQLException if a database access error occurs
     */
    boolean updateBorrowings(int userId, int borrowings) throws SQLException;
}
