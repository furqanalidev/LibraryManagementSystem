package com.assignment.service.impl;

import com.assignment.dao.UserDao;
import com.assignment.dao.mysql.MySqlCredentialsDao;
import com.assignment.dao.CredentialsDao;
import com.assignment.dao.UserActivityLogDao;
import com.assignment.data.User;
import com.assignment.data.UserActivityLog;
import com.assignment.service.UserService;
import com.assignment.service.ServiceException;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of the UserService interface.
 *
 * @author MeHeR ALi
 */
public class UserServiceImpl implements UserService {
    
    private final UserDao userDao;
    private final UserActivityLogDao activityLogDao;
    
    public UserServiceImpl(UserDao userDao, UserActivityLogDao activityLogDao) {
        this.userDao = userDao;
        this.activityLogDao = activityLogDao;
    }
    
    @Override
    public User registerUser(User user, String password) throws ServiceException {
        try {
            // Check if user with same username email or CNIC exists
            if (userDao.findByUsername(user.getUsername()).isPresent()) {
                throw new ServiceException("Username already registered");
            }
            if (userDao.findByEmail(user.getEmail()).isPresent()) {
                throw new ServiceException("Email already registered");
            }
            if (userDao.findByCnic(user.getCnic()).isPresent()) {
                throw new ServiceException("CNIC already registered");
            }
            CredentialsDao credentialsDao = new MySqlCredentialsDao(DatabaseConnectionServiceImpl.newConnection());
            credentialsDao.create(user.getUsername(), password);
            return userDao.save(user);
        } catch (SQLException e) {
            throw new ServiceException("Failed to register user", e);
        }
    }
    
    @Override
    public boolean updateUser(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (SQLException e) {
            throw new ServiceException("Failed to update user", e);
        }
    }
    
    @Override
    public List<UserActivityLog> getUserActivity(int userId) throws ServiceException {
        try {
            return activityLogDao.findByUserId(userId);
        } catch (SQLException e) {
            throw new ServiceException("Failed to get user activity", e);
        }
    }
    
    @Override
    public User findByEmail(String email) throws ServiceException {
        try {
            return userDao.findByEmail(email)
                .orElseThrow(() -> new ServiceException("User not found with email: " + email));
        } catch (SQLException e) {
            throw new ServiceException("Failed to find user by email", e);
        }
    }
    
    @Override
    public User findByCnic(long cnic) throws ServiceException {
        try {
            return userDao.findByCnic(cnic)
                .orElseThrow(() -> new ServiceException("User not found with CNIC: " + cnic));
        } catch (SQLException e) {
            throw new ServiceException("Failed to find user by CNIC", e);
        }
    }

    @Override
    public User findByUsername(String username) throws ServiceException {
        try {
            return userDao.findByUsername(username)
                .orElseThrow(() -> new ServiceException("User not found with username: " + username));
        } catch (SQLException e) {
            throw new ServiceException("Failed to find user by username", e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (SQLException e) {
            throw new ServiceException("Failed to fetch all users", e);
        }
    }
}
