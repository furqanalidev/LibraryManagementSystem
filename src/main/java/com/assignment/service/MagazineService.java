package com.assignment.service;

import com.assignment.data.Magazine;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import java.util.List;

/**
 * Service interface for managing magazine-related operations.
 *
 * @author MeHeR ALi
 */
public interface MagazineService {
    
    /**
     * Adds a new magazine to the library.
     *
     * @param magazine the magazine to add
     * @return the added magazine with generated ID
     * @throws ServiceException if operation fails
     */
    Magazine addMagazine(Magazine magazine) throws ServiceException;
    
    /**
     * Updates an existing magazine's information.
     *
     * @param magazine the magazine with updated information
     * @return true if update was successful
     * @throws ServiceException if operation fails
     */
    boolean updateMagazine(Magazine magazine) throws ServiceException;
    
    /**
     * Removes a magazine from the library.
     *
     * @param magazineId the ID of the magazine to remove
     * @return true if removal was successful
     * @throws ServiceException if operation fails
     */
    boolean removeMagazine(int magazineId) throws ServiceException;
    
    /**
     * Searches for magazines by title.
     *
     * @param title the title to search for
     * @return list of matching magazines
     * @throws ServiceException if operation fails
     */
    List<Magazine> searchByTitle(String title) throws ServiceException;
    
    /**
     * Finds magazines by genre.
     *
     * @param genre the genre to search for
     * @return list of magazines in the genre
     * @throws ServiceException if operation fails
     */
    List<Magazine> findByGenre(Genre genre) throws ServiceException;
    
    /**
     * Finds magazines by language.
     *
     * @param language the language to search for
     * @return list of magazines in the language
     * @throws ServiceException if operation fails
     */
    List<Magazine> findByLanguage(Language language) throws ServiceException;
    
    /**
     * Gets all available magazines (copies > 0).
     *
     * @return list of available magazines
     * @throws ServiceException if operation fails
     */
    List<Magazine> getAvailableMagazines() throws ServiceException;
}