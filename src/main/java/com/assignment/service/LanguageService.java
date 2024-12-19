package com.assignment.service;

import com.assignment.data.Language;
import java.util.List;

/**
 * Service interface for managing language-related operations.
 *
 * @author MeHeR ALi
 */
public interface LanguageService {
    
    /**
     * Adds a new language.
     *
     * @param language the language to add
     * @return the added language with generated ID
     * @throws ServiceException if operation fails
     */
    Language addLanguage(Language language) throws ServiceException;
    
    /**
     * Updates an existing language.
     *
     * @param language the language with updated information
     * @return true if update was successful
     * @throws ServiceException if operation fails
     */
    boolean updateLanguage(Language language) throws ServiceException;
    
    /**
     * Removes a language.
     *
     * @param languageId the ID of the language to remove
     * @return true if removal was successful
     * @throws ServiceException if operation fails
     */
    boolean removeLanguage(int languageId) throws ServiceException;
    
    /**
     * Gets all languages.
     *
     * @return list of all languages
     * @throws ServiceException if operation fails
     */
    List<Language> getAllLanguages() throws ServiceException;
    
    /**
     * Finds a language by name.
     *
     * @param name the name to search for
     * @return the found language
     * @throws ServiceException if operation fails
     */
    Language findByName(String name) throws ServiceException;

    /**
     * Finds a language by ID.
     * @param id the ID of the language to find
     * @return the found language
     * @throws ServiceException if operation fails
     */
    Language findById(int id) throws ServiceException;
}
