package com.assignment.service.impl;

import com.assignment.dao.MagazineDao;
import com.assignment.data.Magazine;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import com.assignment.service.MagazineService;
import com.assignment.service.ServiceException;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of the MagazineService interface.
 *
 * @author MeHeR ALi
 */
public class MagazineServiceImpl implements MagazineService {
    
    private final MagazineDao magazineDao;
    
    public MagazineServiceImpl(MagazineDao magazineDao) {
        this.magazineDao = magazineDao;
    }
    
    @Override
    public Magazine addMagazine(Magazine magazine) throws ServiceException {
        try {
            return magazineDao.save(magazine);
        } catch (SQLException e) {
            throw new ServiceException("Failed to add magazine", e);
        }
    }
    
    @Override
    public boolean updateMagazine(Magazine magazine) throws ServiceException {
        try {
            return magazineDao.update(magazine);
        } catch (SQLException e) {
            throw new ServiceException("Failed to update magazine", e);
        }
    }
    
    @Override
    public boolean removeMagazine(int magazineId) throws ServiceException {
        try {
            return magazineDao.delete(magazineId);
        } catch (SQLException e) {
            throw new ServiceException("Failed to remove magazine", e);
        }
    }
    
    @Override
    public List<Magazine> searchByTitle(String title) throws ServiceException {
        try {
            return magazineDao.findByTitle(title);
        } catch (SQLException e) {
            throw new ServiceException("Failed to search magazines by title", e);
        }
    }
    
    @Override
    public List<Magazine> findByGenre(Genre genre) throws ServiceException {
        try {
            return magazineDao.findByGenre(genre);
        } catch (SQLException e) {
            throw new ServiceException("Failed to find magazines by genre", e);
        }
    }
    
    @Override
    public List<Magazine> findByLanguage(Language language) throws ServiceException {
        try {
            return magazineDao.findByLanguage(language);
        } catch (SQLException e) {
            throw new ServiceException("Failed to find magazines by language", e);
        }
    }
    
    @Override
    public List<Magazine> getAvailableMagazines() throws ServiceException {
        try {
            return magazineDao.findAvailable();
        } catch (SQLException e) {
            throw new ServiceException("Failed to get available magazines", e);
        }
    }
}
