package com.assignment.service.impl;

import com.assignment.dao.UserActivityLogDao;
import com.assignment.data.UserActivityLog;
import com.assignment.service.UserActivityLogService;
import com.assignment.service.ServiceException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of the UserActivityLogService interface.
 *
 * @author MeHeR ALi
 */
public class UserActivityLogServiceImpl implements UserActivityLogService {
    
    private final UserActivityLogDao activityLogDao;
    
    public UserActivityLogServiceImpl(UserActivityLogDao activityLogDao) {
        this.activityLogDao = activityLogDao;
    }
    
    @Override
    public UserActivityLog logActivity(UserActivityLog log) throws ServiceException {
        try {
            return activityLogDao.save(log);
        } catch (SQLException e) {
            throw new ServiceException("Failed to log user activity", e);
        }
    }
    
    @Override
    public List<UserActivityLog> getActivitiesByDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate) 
            throws ServiceException {
        try {
            return activityLogDao.findByDateRange(userId, startDate, endDate);
        } catch (SQLException e) {
            throw new ServiceException("Failed to get user activities by date range", e);
        }
    }
    
    @Override
    public List<UserActivityLog> getActivitiesByAction(int userId, UserActivityLog.Action action) 
            throws ServiceException {
        try {
            return activityLogDao.findByAction(userId, action);
        } catch (SQLException e) {
            throw new ServiceException("Failed to get user activities by action", e);
        }
    }
}
