package com.assignment.service;

import com.assignment.data.Genre;
import java.util.List;

/**
 * Service interface for managing genre-related operations.
 *
 * @author MeHeR ALi
 */
public interface GenreService {
    
    /**
     * Adds a new genre.
     *
     * @param genre the genre to add
     * @return the added genre with generated ID
     * @throws ServiceException if operation fails
     */
    Genre addGenre(Genre genre) throws ServiceException;
    
    /**
     * Updates an existing genre.
     *
     * @param genre the genre with updated information
     * @return true if update was successful
     * @throws ServiceException if operation fails
     */
    boolean updateGenre(Genre genre) throws ServiceException;
    
    /**
     * Removes a genre.
     *
     * @param genreId the ID of the genre to remove
     * @return true if removal was successful
     * @throws ServiceException if operation fails
     */
    boolean removeGenre(int genreId) throws ServiceException;
    
    /**
     * Gets all genres.
     *
     * @return list of all genres
     * @throws ServiceException if operation fails
     */
    List<Genre> getAllGenres() throws ServiceException;
    
    /**
     * Finds a genre by name.
     *
     * @param name the name to search for
     * @return the found genre
     * @throws ServiceException if operation fails
     */
    Genre findByName(String name) throws ServiceException;
}
