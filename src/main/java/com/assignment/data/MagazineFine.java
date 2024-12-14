package com.assignment.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a fine associated with a magazine borrowing in the library system.
 * Extends the base Fine class with magazine-specific attributes.
 *
 * @author MeHeR ALi
 * @see Fine
 */
public class MagazineFine extends Fine {
    
    /** The ID of the associated user magazine borrow record */
    private final int userMagazineBorrowId;

    /**
     * Creates a new MagazineFine instance for database insertion.
     *
     * @param id the unique identifier for this fine
     * @param amount the monetary amount of the fine
     * @param status the payment status of the fine
     * @param reason the reason for the fine
     * @param userMagazineBorrowId the ID of the associated user magazine borrow record
     */
    public MagazineFine(int id, BigDecimal amount, FineStatus status, String reason,
                       int userMagazineBorrowId) {
        this(id, amount, status, reason, userMagazineBorrowId, null);
    }

    /**
     * Creates a MagazineFine instance from database record.
     *
     * @param id the unique identifier for this fine
     * @param amount the monetary amount of the fine
     * @param status the payment status of the fine
     * @param reason the reason for the fine
     * @param userMagazineBorrowId the ID of the associated user magazine borrow record
     * @param createdAt timestamp when record was created
     */
    public MagazineFine(int id, BigDecimal amount, FineStatus status, String reason,
                       int userMagazineBorrowId, LocalDateTime createdAt) {
        super(id, amount, status, reason, createdAt);
        this.userMagazineBorrowId = userMagazineBorrowId;
    }

    /**
     * Gets the ID of the associated user magazine borrow record.
     *
     * @return the user magazine borrow ID
     */
    public int getUserMagazineBorrowId() {
        return userMagazineBorrowId;
    }
}