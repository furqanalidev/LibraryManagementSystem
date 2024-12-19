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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Genre other = (Genre) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
