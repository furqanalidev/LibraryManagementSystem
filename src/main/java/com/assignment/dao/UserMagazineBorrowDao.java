package com.assignment.dao;

import com.assignment.data.UserMagazineBorrow;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for UserMagazineBorrow entity operations.
 *
 * @author MeHeR ALi
 */
public interface UserMagazineBorrowDao extends Dao<UserMagazineBorrow> {
    
    /**
     * Finds all magazine borrows for a specific user.
     *
     * @param userId the ID of the user
     * @return list of magazine borrows for the specified user
     * @throws SQLException if a database access error occurs
     */
    List<UserMagazineBorrow> findByUserId(int userId) throws SQLException;
    
    /**
     * Finds all users for a specific magazine borrow.
     *
     * @param magazineBorrowId the ID of the magazine borrow
     * @return list of user magazine borrows for the specified magazine borrow
     * @throws SQLException if a database access error occurs
     */
    List<UserMagazineBorrow> findByMagazineBorrowId(int magazineBorrowId) throws SQLException;
}
