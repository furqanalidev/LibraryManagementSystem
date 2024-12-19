package com.assignment.dao;

import java.sql.SQLException;

/**
 * Interface for credentials data access.
 * @author  MeHeR ALi
 */
public interface CredentialsDao {
    /**
     *  Checks if the provided credentials are valid.
     * @param username the username to validate
     * @param password the password to validate
     * @return return true if the credentials are valid, false otherwise
     * @throws SQLException if a database access error occurs
     */
    boolean validate (String username, String password) throws SQLException;    
}
