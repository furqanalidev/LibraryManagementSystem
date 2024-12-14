package com.assignment.service.impl;

import com.assignment.dao.LanguageDao;
import com.assignment.data.Language;
import com.assignment.service.LanguageService;
import com.assignment.service.ServiceException;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of the LanguageService interface.
 *
 * @author MeHeR ALi
 */
public class LanguageServiceImpl implements LanguageService {
    
    private final LanguageDao languageDao;
    
    public LanguageServiceImpl(LanguageDao languageDao) {
        this.languageDao = languageDao;
    }
    
    @Override
    public Language addLanguage(Language language) throws ServiceException {
        try {
            if (languageDao.findByName(language.getName()).isPresent()) {
                throw new ServiceException("Language already exists with name: " + language.getName());
            }
            return languageDao.save(language);
        } catch (SQLException e) {
            throw new ServiceException("Failed to add language", e);
        }
    }
    
    @Override
    public boolean updateLanguage(Language language) throws ServiceException {
        try {
            return languageDao.update(language);
        } catch (SQLException e) {
            throw new ServiceException("Failed to update language", e);
        }
    }
    
    @Override
    public boolean removeLanguage(int languageId) throws ServiceException {
        try {
            return languageDao.delete(languageId);
        } catch (SQLException e) {
            throw new ServiceException("Failed to remove language", e);
        }
    }
    
    @Override
    public List<Language> getAllLanguages() throws ServiceException {
        try {
            return languageDao.findAll();
        } catch (SQLException e) {
            throw new ServiceException("Failed to get all languages", e);
        }
    }
    
    @Override
    public Language findByName(String name) throws ServiceException {
        try {
            return languageDao.findByName(name)
                .orElseThrow(() -> new ServiceException("Language not found with name: " + name));
        } catch (SQLException e) {
            throw new ServiceException("Failed to find language by name", e);
        }
    }
}
