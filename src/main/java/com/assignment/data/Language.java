package com.assignment.data;

/**
 * Represents a language option in the library system.
 * This class extends GeneralEntity to inherit ID functionality.
 * 
 * @author MeHeR ALi
 * @see GeneralEntity
 */
public class Language extends GeneralEntity {
    /** Name of the language */
    private final String name;

    /**
     * Creates a new Language instance.
     *
     * @param id   unique identifier for the language
     * @param name name of the language
     */
    public Language(int id, String name) {
        super(id);
        this.name = name;
    }

    /**
     * @return name of the language
     */
    public String getName() {
        return name;
    }
}
