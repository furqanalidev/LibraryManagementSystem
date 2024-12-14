package com.assignment.data;

/**
 * Base class for entities that require an ID field.
 * Provides common ID functionality for entity classes in the system.
 * 
 * @author MeHeR ALi
 */
public class GeneralEntity {
    /** The unique identifier for the entity */
    private int id;

    /**
     * Constructs a new GeneralEntity with the specified ID.
     * 
     * @param id the unique identifier for this entity
     */
    public GeneralEntity(int id) {
        this.id = id;
    }

    /**
     * Returns the ID of this entity.
     * 
     * @return the unique identifier of the entity
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID for this entity.
     * 
     * @param id the new unique identifier to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
