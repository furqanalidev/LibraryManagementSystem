package com.assignment.service;

import java.sql.Connection;

/**
 * Service interface for managing database connections.
 *
 * @author MeHeR ALi
 */
public interface DatabaseConnectionService {
    
    /**
     * Gets a connection to the database.
     *
     * @return an active database connection
     * @throws ServiceException if connection fails
     */
    Connection getConnection() throws ServiceException;
    
    /**
     * Closes all open connections.
     *
     * @throws ServiceException if closing fails
     */
    void closeConnections() throws ServiceException;
    
    /**
     * Checks if database connection is valid.
     *
     * @return true if connection is valid
     * @throws ServiceException if check fails
     */
    boolean testConnection() throws ServiceException;
}
