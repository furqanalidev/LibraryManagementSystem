package com.assignment.service;

import com.assignment.data.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

/**
 * Service interface for generating library reports and statistics.
 *
 * @author MeHeR ALi
 */
public interface ReportService {
    
    /**
     * Generates borrowing statistics for a date range.
     *
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return map of item types to borrow counts
     * @throws ServiceException if operation fails
     */
    Map<String, Integer> getBorrowingStats(LocalDateTime startDate, LocalDateTime endDate) throws ServiceException;
    
    /**
     * Gets most borrowed books.
     *
     * @param limit number of books to return
     * @return list of books with their borrow counts
     * @throws ServiceException if operation fails
     */
    List<Map.Entry<Book, Integer>> getMostBorrowedBooks(int limit) throws ServiceException;
    
    /**
     * Gets most active users.
     *
     * @param limit number of users to return
     * @return list of users with their activity counts
     * @throws ServiceException if operation fails
     */
    List<Map.Entry<User, Integer>> getMostActiveUsers(int limit) throws ServiceException;
    
    /**
     * Gets fine collection statistics.
     *
     * @param startDate start of the date range
     * @param endDate end of the date range
     * @return total fines collected and pending
     * @throws ServiceException if operation fails
     */
    Map<String, BigDecimal> getFineStats(LocalDateTime startDate, LocalDateTime endDate) throws ServiceException;
}
