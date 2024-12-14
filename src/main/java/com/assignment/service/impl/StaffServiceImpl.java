package com.assignment.service.impl;

import com.assignment.dao.StaffDao;
import com.assignment.data.Staff;
import com.assignment.service.StaffService;
import com.assignment.service.ServiceException;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of the StaffService interface.
 *
 * @author MeHeR ALi
 */
public class StaffServiceImpl implements StaffService {
    
    private final StaffDao staffDao;
    
    public StaffServiceImpl(StaffDao staffDao) {
        this.staffDao = staffDao;
    }
    
    @Override
    public Staff registerStaff(Staff staff) throws ServiceException {
        try {
            // Check if staff with same email exists
            if (staffDao.findByEmail(staff.getEmail()).isPresent()) {
                throw new ServiceException("Email already registered");
            }
            if (staffDao.findByCnic(staff.getCnic()).isPresent()) {
                throw new ServiceException("CNIC already registered");
            }
            
            return staffDao.save(staff);
        } catch (SQLException e) {
            throw new ServiceException("Failed to register staff member", e);
        }
    }
    
    @Override
    public boolean updateStaff(Staff staff) throws ServiceException {
        try {
            return staffDao.update(staff);
        } catch (SQLException e) {
            throw new ServiceException("Failed to update staff member", e);
        }
    }
    
    @Override
    public Staff findByEmail(String email) throws ServiceException {
        try {
            return staffDao.findByEmail(email)
                .orElseThrow(() -> new ServiceException("Staff member not found with email: " + email));
        } catch (SQLException e) {
            throw new ServiceException("Failed to find staff member by email", e);
        }
    }
    
    @Override
    public List<Staff> findByOccupation(Staff.Occupation occupation) throws ServiceException {
        try {
            return staffDao.findByOccupation(occupation);
        } catch (SQLException e) {
            throw new ServiceException("Failed to find staff members by occupation", e);
        }
    }
}
