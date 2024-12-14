package com.assignment.dao;

import com.assignment.data.Genre;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Data Access Object interface for Genre entity operations.
 *
 * @author MeHeR ALi
 */
public interface GenreDao extends Dao<Genre> {
    
    /**
     * Finds a genre by its name.
     *
     * @param name the genre name to search for
     * @return Optional containing the found genre or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<Genre> findByName(String name) throws SQLException;
}
