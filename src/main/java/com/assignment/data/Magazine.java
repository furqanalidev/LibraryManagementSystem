package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Represents a magazine in the library system.
 * This class extends LibraryItem to inherit common library material functionality.
 * 
 * @author MeHeR ALi
 * @see LibraryItem
 */
public class Magazine extends LibraryItem {
    /**
     * Creates a new Magazine instance for database insertion.
     *
     * @param id             unique identifier for the magazine
     * @param title          title of the magazine
     * @param availableCopies number of copies available
     * @param isBorrowable   whether magazine can be borrowed
     * @param genre          genre classification
     * @param language       language of the magazine
     */
    public Magazine(int id, String title, int availableCopies, 
                   boolean isBorrowable, Genre genre, Language language) {
        super(id, title, availableCopies, isBorrowable, genre, language);
    }

    /**
     * Creates a Magazine instance from database record.
     *
     * @param id             unique identifier for the magazine
     * @param title          title of the magazine
     * @param availableCopies number of copies available
     * @param isBorrowable   whether magazine can be borrowed
     * @param genre          genre classification
     * @param language       language of the magazine
     * @param createdAt      timestamp when record was created
     * @param updatedAt      timestamp when record was last updated
     */
    public Magazine(int id, String title, int availableCopies, 
                   boolean isBorrowable, Genre genre, Language language,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, title, availableCopies, isBorrowable, genre, language, createdAt, updatedAt);
    }
}