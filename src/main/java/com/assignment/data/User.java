package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Represents a library user with borrowing privileges.
 * This class extends Person to inherit personal information while adding
 * library-specific functionality for managing borrowing limits and tracking.
 * 
 * @author MeHeR ALi
 * @see Person
 */
public class User extends Person {
    // Borrowing Status
    /** Current number of items borrowed by the user */
    private final int borrowings;
    /** Maximum number of books this user can borrow simultaneously */
    private final int bookBorrowLimit;
    /** Maximum number of magazines this user can borrow simultaneously */
    private final int magazineBorrowLimit;
    
    /**
     * Creates a new User instance for database insertion.
     * The createdAt timestamp will be automatically set by the database.
     *
     * @param id                  unique identifier for the user
     * @param firstName           user's legal first name
     * @param lastName            user's legal last name
     * @param cnic                unique national identity number
     * @param address             current residential address
     * @param contact             primary contact number
     * @param email               primary email address
     * @param borrowings          current number of borrowed items
     * @param bookBorrowLimit     maximum allowed book borrowings
     * @param magazineBorrowLimit maximum allowed magazine borrowings
     */
    public User(int id, String firstName, String lastName, Long cnic, 
                String address, String contact, String email, 
                int borrowings, int bookBorrowLimit, int magazineBorrowLimit) {
        this(id, firstName, lastName, cnic, address, contact, email, 
             borrowings, bookBorrowLimit, magazineBorrowLimit, null);
    }

    /**
     * Creates a User instance from existing database record.
     * Used when retrieving records that already have a creation timestamp.
     *
     * @param id                  unique identifier for the user
     * @param firstName           user's legal first name
     * @param lastName            user's legal last name
     * @param cnic                unique national identity number
     * @param address             current residential address
     * @param contact             primary contact number
     * @param email               primary email address
     * @param borrowings          current number of borrowed items
     * @param bookBorrowLimit     maximum allowed book borrowings
     * @param magazineBorrowLimit maximum allowed magazine borrowings
     * @param createdAt           timestamp when record was created
     */
    public User(int id, String firstName, String lastName, Long cnic, 
                String address, String contact, String email, 
                int borrowings, int bookBorrowLimit, int magazineBorrowLimit,
                LocalDateTime createdAt) {
        super(id, firstName, lastName, cnic, address, contact, email, createdAt);
        this.borrowings = borrowings;
        this.bookBorrowLimit = bookBorrowLimit;
        this.magazineBorrowLimit = magazineBorrowLimit;
    }

    /**
     * @return current number of items borrowed by the user
     */
    public int getBorrowings() {
        return borrowings;
    }

    /**
     * @return maximum number of books this user can borrow
     */
    public int getBookBorrowLimit() {
        return bookBorrowLimit;
    }

    /**
     * @return maximum number of magazines this user can borrow
     */
    public int getMagazineBorrowLimit() {
        return magazineBorrowLimit;
    }
}