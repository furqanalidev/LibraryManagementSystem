package com.assignment.dao;

import com.assignment.data.Language;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Data Access Object interface for Language entity operations.
 *
 * @author MeHeR ALi
 */
public interface LanguageDao extends Dao<Language> {
    
    /**
     * Finds a language by its name.
     *
     * @param name the language name to search for
     * @return Optional containing the found language or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<Language> findByName(String name) throws SQLException;
}
