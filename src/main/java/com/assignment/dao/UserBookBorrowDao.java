package com.assignment.dao;

import com.assignment.data.UserBookBorrow;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for UserBookBorrow entity operations.
 *
 * @author MeHeR ALi
 */
public interface UserBookBorrowDao extends Dao<UserBookBorrow> {
    
    /**
     * Finds all book borrows for a specific user.
     *
     * @param userId the ID of the user
     * @return list of book borrows for the specified user
     * @throws SQLException if a database access error occurs
     */
    List<UserBookBorrow> findByUserId(int userId) throws SQLException;
    
    /**
     * Finds all users for a specific book borrow.
     *
     * @param bookBorrowId the ID of the book borrow
     * @return list of user book borrows for the specified book borrow
     * @throws SQLException if a database access error occurs
     */
    List<UserBookBorrow> findByBookBorrowId(int bookBorrowId) throws SQLException;
}
