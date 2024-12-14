package com.assignment.data;

/**
 * Represents a book genre category in the library system.
 * This class extends GeneralEntity to inherit ID functionality.
 * 
 * @author MeHeR ALi
 * @see GeneralEntity
 */
public class Genre extends GeneralEntity {
    /** Name of the genre category */
    private final String name;

    /**
     * Creates a new Genre instance.
     *
     * @param id   unique identifier for the genre
     * @param name name of the genre category
     */
    public Genre(int id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * @return name of the genre category
     */
    public String getName() {
        return name;
    }
}
