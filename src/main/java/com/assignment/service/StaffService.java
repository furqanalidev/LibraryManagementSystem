package com.assignment.service;

import com.assignment.data.Staff;
import java.util.List;

/**
 * Service interface for managing staff-related operations.
 *
 * @author MeHeR ALi
 */
public interface StaffService {
    
    /**
     * Registers a new staff member.
     *
     * @param staff the staff member to register
     * @return the registered staff member with generated ID
     * @throws ServiceException if operation fails
     */
    Staff registerStaff(Staff staff, String password) throws ServiceException;
    
    /**
     * Updates staff member information.
     *
     * @param staff the staff member with updated information
     * @return true if update was successful
     * @throws ServiceException if operation fails
     */
    boolean updateStaff(Staff staff) throws ServiceException;
    
    /**
     * Finds a staff member by their email.
     *
     * @param email the email to search for
     * @return the found staff member
     * @throws ServiceException if operation fails
     */
    Staff findByEmail(String email) throws ServiceException;
    
    /**
     * Finds staff members by their occupation.
     *
     * @param occupation the occupation to search for
     * @return list of staff members with the specified occupation
     * @throws ServiceException if operation fails
     */
    List<Staff> findByOccupation(Staff.Occupation occupation) throws ServiceException;
    Staff findByUsername(String uesrname) throws ServiceException;
    List<Staff> findAll() throws ServiceException;
}
