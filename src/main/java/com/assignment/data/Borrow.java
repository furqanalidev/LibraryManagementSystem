package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Abstract base class for all borrowing transactions in the library system.
 * Provides common attributes and functionality for tracking item borrowings.
 * 
 * @author MeHeR ALi
 * @see GeneralEntity
 */
public abstract class Borrow extends GeneralEntity {
    /**
     * Represents the possible states of a borrowing transaction
     */
    public enum Status {
        PENDING,
        BORROWED,
        RETURNED,
        OVERDUE
    }

    /** Date and time of the borrowing transaction */
    private final LocalDateTime date;
    /** Current status of the borrowing */
    private final Status status;
    /** Staff member who processed the transaction */
    private final Staff staff;
    /** Timestamp when the record was created */
    private final LocalDateTime createdAt;
    /** Timestamp when the record was last updated */
    private final LocalDateTime updatedAt;

    /**
     * Creates a new Borrow instance for database insertion.
     *
     * @param id     unique identifier for the transaction
     * @param date   date and time of the borrowing
     * @param status current status of the borrowing
     * @param staff  staff member processing the transaction
     */
    public Borrow(int id, LocalDateTime date, Status status, Staff staff) {
        this(id, date, status, staff, null, null);
    }

    /**
     * Creates a Borrow instance from database record.
     *
     * @param id        unique identifier for the transaction
     * @param date      date and time of the borrowing
     * @param status    current status of the borrowing
     * @param staff     staff member processing the transaction
     * @param createdAt timestamp when record was created
     * @param updatedAt timestamp when record was last updated
     */
    public Borrow(int id, LocalDateTime date, Status status, Staff staff,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.date = date;
        this.status = status;
        this.staff = staff;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * @return date and time of the borrowing
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @return current status of the borrowing
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return staff member who processed the transaction
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     * @return timestamp when record was created
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return timestamp when record was last updated
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
