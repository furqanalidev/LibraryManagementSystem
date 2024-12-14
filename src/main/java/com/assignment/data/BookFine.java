package com.assignment.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a fine associated with a book borrowing in the library system.
 * Extends the base Fine class with book-specific attributes.
 *
 * @author MeHeR ALi
 * @see Fine
 */
public class BookFine extends Fine {
    
    /** The ID of the associated user book borrow record */
    private final int userBookBorrowId;

    /**
     * Creates a new BookFine instance for database insertion.
     *
     * @param id the unique identifier for this fine
     * @param amount the monetary amount of the fine
     * @param status the payment status of the fine
     * @param reason the reason for the fine
     * @param userBookBorrowId the ID of the associated user book borrow record
     */
    public BookFine(int id, BigDecimal amount, FineStatus status, String reason, 
                   int userBookBorrowId) {
        this(id, amount, status, reason, userBookBorrowId, null);
    }

    /**
     * Creates a BookFine instance from database record.
     *
     * @param id the unique identifier for this fine
     * @param amount the monetary amount of the fine
     * @param status the payment status of the fine
     * @param reason the reason for the fine
     * @param userBookBorrowId the ID of the associated user book borrow record
     * @param createdAt timestamp when record was created
     */
    public BookFine(int id, BigDecimal amount, FineStatus status, String reason,
                   int userBookBorrowId, LocalDateTime createdAt) {
        super(id, amount, status, reason, createdAt);
        this.userBookBorrowId = userBookBorrowId;
    }

    /**
     * Gets the ID of the associated user book borrow record.
     *
     * @return the user book borrow ID
     */
    public int getUserBookBorrowId() {
        return userBookBorrowId;
    }
}