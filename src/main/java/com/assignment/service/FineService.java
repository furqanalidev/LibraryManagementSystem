package com.assignment.service;

import com.assignment.data.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service interface for managing fine-related operations.
 *
 * @author MeHeR ALi
 */
public interface FineService {
    
    /**
     * Issues a fine for a book borrowing.
     *
     * @param userBookBorrowId the ID of the user book borrow record
     * @param amount the fine amount
     * @param reason the reason for the fine
     * @return the created fine record
     * @throws ServiceException if operation fails
     */
    BookFine issueBookFine(int userBookBorrowId, BigDecimal amount, String reason) throws ServiceException;
    
    /**
     * Issues a fine for a magazine borrowing.
     *
     * @param userMagazineBorrowId the ID of the user magazine borrow record
     * @param amount the fine amount
     * @param reason the reason for the fine
     * @return the created fine record
     * @throws ServiceException if operation fails
     */
    MagazineFine issueMagazineFine(int userMagazineBorrowId, BigDecimal amount, String reason) throws ServiceException;
    
    /**
     * Pays a book fine.
     *
     * @param fineId the ID of the fine record
     * @return true if payment was successful
     * @throws ServiceException if operation fails
     */
    boolean payBookFine(int fineId) throws ServiceException;
    
    /**
     * Pays a magazine fine.
     *
     * @param fineId the ID of the fine record
     * @return true if payment was successful
     * @throws ServiceException if operation fails
     */
    boolean payMagazineFine(int fineId) throws ServiceException;
    
    /**
     * Gets all unpaid fines for a user.
     *
     * @param userId the ID of the user
     * @return list of unpaid fines
     * @throws ServiceException if operation fails
     */
    List<Fine> getUnpaidFines(int userId) throws ServiceException;
}
