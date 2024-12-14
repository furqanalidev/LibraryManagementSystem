package com.assignment.dao;

import com.assignment.data.Genre;
import com.assignment.data.Language;
import com.assignment.data.Magazine;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Magazine entity operations.
 *
 * @author MeHeR ALi
 */
public interface MagazineDao extends Dao<Magazine> {

    /**
     * Finds all magazines by a specific genre.
     *
     * @param genreId the ID of the genre to search for
     * @return list of magazines in the genre
     * @throws SQLException if a database access error occurs
     */
    List<Magazine> findByGenre(int genreId) throws SQLException;

    /**
     * Updates the available copies count for a magazine.
     *
     * @param magazineId the ID of the magazine
     * @param count      the new available copies count
     * @return true if update was successful
     * @throws SQLException if a database access error occurs
     */
    boolean updateAvailableCopies(int magazineId, int count) throws SQLException;

    List<Magazine> findByTitle(String title) throws SQLException;

    List<Magazine> findByLanguage(Language language) throws SQLException;

    List<Magazine> findAvailable() throws SQLException;

    List<Magazine> findByGenre(Genre genre) throws SQLException;
}
