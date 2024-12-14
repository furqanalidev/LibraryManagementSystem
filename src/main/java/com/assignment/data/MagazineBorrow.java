package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Represents a magazine borrowing transaction in the library system.
 * This class extends Borrow to inherit common borrowing functionality.
 * 
 * @author MeHeR ALi
 * @see Borrow
 */
public class MagazineBorrow extends Borrow {
    /** The magazine being borrowed */
    private final Magazine magazine;

    /**
     * Creates a new MagazineBorrow instance for database insertion.
     *
     * @param id       unique identifier for the transaction
     * @param date     date and time of the borrowing
     * @param status   current status of the borrowing
     * @param magazine magazine being borrowed
     * @param staff    staff member processing the transaction
     */
    public MagazineBorrow(int id, LocalDateTime date, Status status, 
                         Magazine magazine, Staff staff) {
        this(id, date, status, magazine, staff, null, null);
    }

    /**
     * Creates a MagazineBorrow instance from database record.
     *
     * @param id        unique identifier for the transaction
     * @param date      date and time of the borrowing
     * @param status    current status of the borrowing
     * @param magazine  magazine being borrowed
     * @param staff     staff member processing the transaction
     * @param createdAt timestamp when record was created
     * @param updatedAt timestamp when record was last updated
     */
    public MagazineBorrow(int id, LocalDateTime date, Status status, 
                         Magazine magazine, Staff staff,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, date, status, staff, createdAt, updatedAt);
        this.magazine = magazine;
    }

    /**
     * @return the magazine being borrowed
     */
    public Magazine getMagazine() {
        return magazine;
    }
}
