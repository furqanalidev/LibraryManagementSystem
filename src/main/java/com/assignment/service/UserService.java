package com.assignment.service;

import com.assignment.data.User;
import com.assignment.data.UserActivityLog;
import java.util.List;

/**
 * Service interface for managing user-related operations.
 *
 * @author MeHeR ALi
 */
public interface UserService {
    
    /**
     * Registers a new user in the system.
     *
     * @param user the user to register
     * @return the registered user with generated ID
     * @throws ServiceException if operation fails
     */
    User registerUser(User user) throws ServiceException;
    
    /**
     * Updates user information.
     *
     * @param user the user with updated information
     * @return true if update was successful
     * @throws ServiceException if operation fails
     */
    boolean updateUser(User user) throws ServiceException;
    
    /**
     * Gets user activity history.
     *
     * @param userId the ID of the user
     * @return list of user activity logs
     * @throws ServiceException if operation fails
     */
    List<UserActivityLog> getUserActivity(int userId) throws ServiceException;
    
    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for
     * @return the found user
     * @throws ServiceException if operation fails
     */
    User findByEmail(String email) throws ServiceException;
    
    /**
     * Finds a user by their CNIC.
     *
     * @param cnic the CNIC to search for
     * @return the found user
     * @throws ServiceException if operation fails
     */
    User findByCnic(long cnic) throws ServiceException;
    User findByUsername(String uesrname) throws ServiceException;
}
