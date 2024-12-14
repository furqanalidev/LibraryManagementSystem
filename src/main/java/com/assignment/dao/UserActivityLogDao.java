package com.assignment.dao;

import com.assignment.data.UserActivityLog;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Access Object interface for UserActivityLog entity operations.
 *
 * @author MeHeR ALi
 */
public interface UserActivityLogDao extends Dao<UserActivityLog> {

    /**
     * Finds all activity logs for a specific user.
     *
     * @param userId the ID of the user
     * @return list of activity logs for the specified user
     * @throws SQLException if a database access error occurs
     */
    List<UserActivityLog> findByUserId(int userId) throws SQLException;

    /**
     * Finds all activity logs by action type.
     *
     * @param action the type of action
     * @return list of activity logs for the specified action
     * @throws SQLException if a database access error occurs
     */
    List<UserActivityLog> findByAction(UserActivityLog.Action action) throws SQLException;

    /**
     * Finds all activity logs by reference type.
     *
     * @param referenceType the type of reference
     * @return list of activity logs for the specified reference type
     * @throws SQLException if a database access error occurs
     */
    List<UserActivityLog> findByReferenceType(UserActivityLog.ReferenceType referenceType) throws SQLException;

    List<UserActivityLog> findByDateRange(int userId, LocalDateTime startDate, LocalDateTime endDate)
            throws SQLException;

    List<UserActivityLog> findByAction(int userId, UserActivityLog.Action action) throws SQLException;
}
