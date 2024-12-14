package com.assignment.dao;

import com.assignment.data.BookBorrow;
import com.assignment.data.Borrow;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for BookBorrow entity operations.
 *
 * @author MeHeR ALi
 */
public interface BookBorrowDao extends Dao<BookBorrow> {
    
    /**
     * Finds all borrows by their current status.
     *
     * @param status the status to search for
     * @return list of book borrows with the specified status
     * @throws SQLException if a database access error occurs
     */
    List<BookBorrow> findByStatus(Borrow.Status status) throws SQLException;
    
    /**
     * Finds all borrows for a specific book.
     *
     * @param bookId the ID of the book
     * @return list of book borrows for the specified book
     * @throws SQLException if a database access error occurs
     */
    List<BookBorrow> findByBookId(int bookId) throws SQLException;
    
    /**
     * Updates the status of a book borrow.
     *
     * @param borrowId the ID of the borrow record
     * @param status the new status
     * @return true if update was successful
     * @throws SQLException if a database access error occurs
     */
    boolean updateStatus(int borrowId, Borrow.Status status) throws SQLException;
}
