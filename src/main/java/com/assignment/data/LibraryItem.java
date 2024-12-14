package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Abstract base class for all library items (books, magazines, etc.).
 * Provides common attributes and functionality for library materials.
 * 
 * @author MeHeR ALi
 * @see GeneralEntity
 */
public abstract class LibraryItem extends GeneralEntity {
    /** Title of the library item */
    private final String title;
    /** Number of copies available for borrowing */
    private int availableCopies;
    /** Whether this item can be borrowed */
    private final boolean isBorrowable;
    /** Genre classification of the item */
    private final Genre genre;
    /** Language of the item */
    private final Language language;
    /** Timestamp when the record was created */
    private final LocalDateTime createdAt;
    /** Timestamp when the record was last updated */
    private final LocalDateTime updatedAt;

    /**
     * Creates a new LibraryItem instance for database insertion.
     * The attributes createdAt and updatedAt are set by database.
     *
     * @param id             unique identifier for the item
     * @param title          title of the item
     * @param availableCopies number of copies available
     * @param isBorrowable   whether item can be borrowed
     * @param genre          genre classification
     * @param language       language of the item
     */
    public LibraryItem(int id, String title, int availableCopies, 
                      boolean isBorrowable, Genre genre, Language language) {
        this(id, title, availableCopies, isBorrowable, genre, language, null, null);
    }

    /**
     * Creates a LibraryItem instance from database record.
     *
     * @param id             unique identifier for the item
     * @param title          title of the item
     * @param availableCopies number of copies available
     * @param isBorrowable   whether item can be borrowed
     * @param genre          genre classification
     * @param language       language of the item
     * @param createdAt      timestamp when record was created
     * @param updatedAt      timestamp when record was last updated
     */
    public LibraryItem(int id, String title, int availableCopies, 
                      boolean isBorrowable, Genre genre, Language language,
                      LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.title = title;
        this.availableCopies = availableCopies;
        this.isBorrowable = isBorrowable;
        this.genre = genre;
        this.language = language;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * @return title of the library item
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return number of copies available for borrowing
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * @return whether this item can be borrowed
     */
    public boolean isBorrowable() {
        return isBorrowable;
    }

    /**
     * @return genre classification of the item
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * @return language of the item
     */
    public Language getLanguage() {
        return language;
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

    /**
     * Updates the number of available copies
     * @param availableCopies new number of available copies
     */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}
