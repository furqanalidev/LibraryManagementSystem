package com.assignment.data;

import java.time.LocalDateTime;
import java.time.Year;

/**
 * Represents a book in the library system.
 * This class extends LibraryItem to inherit common library material functionality.
 * 
 * @author MeHeR ALi
 * @see LibraryItem
 */
public class Book extends LibraryItem {
    /** International Standard Book Number - unique identifier for books */
    private final String isbn;
    /** Author of the book */
    private final String author;
    /** Publisher of the book */
    private final String publisher;
    /** Publication year of the book */
    private final Year year;

    /**
     * Creates a new Book instance for database insertion.
     *
     * @param id             unique identifier for the book
     * @param isbn           unique ISBN for the book
     * @param title          title of the book
     * @param author         author of the book
     * @param publisher      publisher of the book
     * @param language       language of the book
     * @param year          publication year
     * @param availableCopies number of copies available
     * @param genre          genre classification
     * @param isBorrowable   whether book can be borrowed
     */
    public Book(int id, String isbn, String title, String author, String publisher,
                Language language, Year year, int availableCopies, Genre genre, 
                boolean isBorrowable) {
        this(id, isbn, title, author, publisher, language, year, 
             availableCopies, genre, isBorrowable, null, null);
    }

    /**
     * Creates a Book instance from database record.
     *
     * @param id             unique identifier for the book
     * @param isbn           unique ISBN for the book
     * @param title          title of the book
     * @param author         author of the book
     * @param publisher      publisher of the book
     * @param language       language of the book
     * @param year          publication year
     * @param availableCopies number of copies available
     * @param genre          genre classification
     * @param isBorrowable   whether book can be borrowed
     * @param createdAt      timestamp when record was created
     * @param updatedAt      timestamp when record was last updated
     */
    public Book(int id, String isbn, String title, String author, String publisher,
                Language language, Year year, int availableCopies, Genre genre, 
                boolean isBorrowable, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, title, availableCopies, isBorrowable, genre, language, createdAt, updatedAt);
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    /**
     * @return ISBN of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return publisher of the book
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @return publication year of the book
     */
    public Year getYear() {
        return year;
    }
}
