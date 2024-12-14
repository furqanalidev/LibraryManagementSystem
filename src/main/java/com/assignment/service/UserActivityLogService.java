package com.assignment.service;

import com.assignment.data.UserActivityLog;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing user activity logging operations.
 *
 * @author MeHeR ALi
 */
public interface UserActivityLogService {
    
    /**
     * Logs a user activity.
     *
     * @param log the activity log entry to save
     * @return the saved log entry with generated ID
     * @throws ServiceException if operation fails
     */
    UserActivityLog logActivity(UserActivityLog log) throws ServiceException;
    
    /**
     * Gets user activities within a date range.
     *
     * @param userId the ID of the user
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return list of activity logs within the date range
     * @throws ServiceException if operation fails
     */
    List<UserActivityLog> getActivitiesByDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate) 
        throws ServiceException;
    
    /**
     * Gets user activities by action type.
     *
     * @param userId the ID of the user
     * @param action the type of action
     * @return list of activity logs for the specified action
     * @throws ServiceException if operation fails
     */
    List<UserActivityLog> getActivitiesByAction(int userId, UserActivityLog.Action action) throws ServiceException;
}
