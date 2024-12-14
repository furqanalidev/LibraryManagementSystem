package com.assignment.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Abstract base class for fine-related entities in the library system.
 * Provides common attributes and functionality for different types of fines.
 * This class serves as the parent for both BookFine and MagazineFine entities.
 *
 * @author MeHeR ALi 
 * @see GeneralEntity
 */
public abstract class Fine extends GeneralEntity {
    
    /** The monetary amount of the fine */
    private final BigDecimal amount;
    
    /** The current payment status of the fine */
    private final FineStatus status;
    
    /** The descriptive reason for why the fine was imposed */
    private final String reason;
    
    /** The date and time when this fine record was created */
    private final LocalDateTime createdAt;

    /**
     * Enumeration defining the possible payment states of a fine.
     * PAID indicates the fine has been settled.
     * UNPAID indicates the fine is still outstanding.
     */
    public enum FineStatus {
        /** Fine has been paid */
        PAID,
        /** Fine is still pending payment */
        UNPAID
    }

    /**
     * Creates a new Fine instance for database insertion.
     *
     * @param id the unique identifier for this fine
     * @param amount the monetary amount of the fine
     * @param status the current payment status of the fine
     * @param reason the descriptive reason for the fine
     */
    public Fine(int id, BigDecimal amount, FineStatus status, String reason) {
        this(id, amount, status, reason, null);
    }

    /**
     * Creates a Fine instance from database record.
     *
     * @param id the unique identifier for this fine
     * @param amount the monetary amount of the fine
     * @param status the current payment status of the fine
     * @param reason the descriptive reason for the fine
     * @param createdAt timestamp when record was created
     */
    public Fine(int id, BigDecimal amount, FineStatus status, String reason, LocalDateTime createdAt) {
        super(id);
        this.amount = amount;
        this.status = status;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the monetary amount of the fine.
     * 
     * @return the fine amount as BigDecimal
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Retrieves the current payment status of the fine.
     * 
     * @return the FineStatus enum value representing current status
     */
    public FineStatus getStatus() {
        return status;
    }

    /**
     * Retrieves the reason for which this fine was imposed.
     * 
     * @return the descriptive reason string
     */
    public String getReason() {
        return reason;
    }

    /**
     * Retrieves the creation timestamp of this fine.
     * 
     * @return the LocalDateTime when this fine was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}