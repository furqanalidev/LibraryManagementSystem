package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Represents a book borrowing transaction in the library system.
 * This class extends Borrow to inherit common borrowing functionality.
 * 
 * @author MeHeR ALi
 * @see Borrow
 */
public class BookBorrow extends Borrow {
    /** The book being borrowed */
    private final Book book;

    /**
     * Creates a new BookBorrow instance for database insertion.
     *
     * @param id     unique identifier for the transaction
     * @param date   date and time of the borrowing
     * @param status current status of the borrowing
     * @param book   book being borrowed
     * @param staff  staff member processing the transaction
     */
    public BookBorrow(int id, LocalDateTime date, Status status, 
                     Book book, Staff staff) {
        this(id, date, status, book, staff, null, null);
    }

    /**
     * Creates a BookBorrow instance from database record.
     *
     * @param id        unique identifier for the transaction
     * @param date      date and time of the borrowing
     * @param status    current status of the borrowing
     * @param book      book being borrowed
     * @param staff     staff member processing the transaction
     * @param createdAt timestamp when record was created
     * @param updatedAt timestamp when record was last updated
     */
    public BookBorrow(int id, LocalDateTime date, Status status, 
                     Book book, Staff staff,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, date, status, staff, createdAt, updatedAt);
        this.book = book;
    }

    /**
     * @return the book being borrowed
     */
    public Book getBook() {
        return book;
    }
}
