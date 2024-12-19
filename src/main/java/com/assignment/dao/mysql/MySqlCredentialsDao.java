package com.assignment.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.assignment.dao.CredentialsDao;

public class MySqlCredentialsDao implements  CredentialsDao {
    private static final String VALIDATE = "SELECT * FROM Credentials WHERE username = ? AND password = ?";

    private final Connection connection;

    public MySqlCredentialsDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean validate(String username, String password) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(VALIDATE)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}
