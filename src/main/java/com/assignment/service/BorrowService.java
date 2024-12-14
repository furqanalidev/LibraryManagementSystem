package com.assignment.service;

import com.assignment.data.*;
import java.util.List;

/**
 * Service interface for managing borrowing operations.
 *
 * @author MeHeR ALi
 */
public interface BorrowService {
    
    /**
     * Processes a book borrowing request.
     *
     * @param user the user borrowing the book
     * @param book the book to borrow
     * @param staff the staff processing the request
     * @return the created book borrow record
     * @throws ServiceException if operation fails
     */
    BookBorrow borrowBook(User user, Book book, Staff staff) throws ServiceException;
    
    /**
     * Processes a magazine borrowing request.
     *
     * @param user the user borrowing the magazine
     * @param magazine the magazine to borrow
     * @param staff the staff processing the request
     * @return the created magazine borrow record
     * @throws ServiceException if operation fails
     */
    MagazineBorrow borrowMagazine(User user, Magazine magazine, Staff staff) throws ServiceException;
    
    /**
     * Returns a borrowed book.
     *
     * @param borrowId the ID of the book borrow record
     * @return true if return was successful
     * @throws ServiceException if operation fails
     */
    boolean returnBook(int borrowId) throws ServiceException;
    
    /**
     * Returns a borrowed magazine.
     *
     * @param borrowId the ID of the magazine borrow record
     * @return true if return was successful
     * @throws ServiceException if operation fails
     */
    boolean returnMagazine(int borrowId) throws ServiceException;
    
    /**
     * Gets all active book borrows for a user.
     *
     * @param userId the ID of the user
     * @return list of active book borrows
     * @throws ServiceException if operation fails
     */
    List<BookBorrow> getActiveBookBorrows(int userId) throws ServiceException;
    
    /**
     * Gets all active magazine borrows for a user.
     *
     * @param userId the ID of the user
     * @return list of active magazine borrows
     * @throws ServiceException if operation fails
     */
    List<MagazineBorrow> getActiveMagazineBorrows(int userId) throws ServiceException;
}
