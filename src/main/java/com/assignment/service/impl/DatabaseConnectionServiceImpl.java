package com.assignment.service.impl;

import com.assignment.service.DatabaseConnectionService;
import com.assignment.service.ServiceException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the DatabaseConnectionService interface.
 *
 * @author MeHeR ALi
 */
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {
    
    private final String url;
    private final String username;
    private final String password;
    private final List<Connection> activeConnections;
    
    public DatabaseConnectionServiceImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.activeConnections = new ArrayList<>();
    }
    
    @Override
    public Connection getConnection() throws ServiceException {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            activeConnections.add(connection);
            return connection;
        } catch (SQLException e) {
            throw new ServiceException("Failed to establish database connection", e);
        }
    }
    
    @Override
    public void closeConnections() throws ServiceException {
        List<SQLException> exceptions = new ArrayList<>();
        
        for (Connection connection : activeConnections) {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                exceptions.add(e);
            }
        }
        
        activeConnections.clear();
        
        if (!exceptions.isEmpty()) {
            throw new ServiceException("Failed to close all database connections", exceptions.get(0));
        }
    }
    
    @Override
    public boolean testConnection() throws ServiceException {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            throw new ServiceException("Failed to test database connection", e);
        }
    }
}
