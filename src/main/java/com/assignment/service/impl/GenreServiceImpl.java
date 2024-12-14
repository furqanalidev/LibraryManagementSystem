package com.assignment.service.impl;

import com.assignment.dao.GenreDao;
import com.assignment.data.Genre;
import com.assignment.service.GenreService;
import com.assignment.service.ServiceException;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of the GenreService interface.
 *
 * @author MeHeR ALi
 */
public class GenreServiceImpl implements GenreService {
    
    private final GenreDao genreDao;
    
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }
    
    @Override
    public Genre addGenre(Genre genre) throws ServiceException {
        try {
            if (genreDao.findByName(genre.getName()).isPresent()) {
                throw new ServiceException("Genre already exists with name: " + genre.getName());
            }
            return genreDao.save(genre);
        } catch (SQLException e) {
            throw new ServiceException("Failed to add genre", e);
        }
    }
    
    @Override
    public boolean updateGenre(Genre genre) throws ServiceException {
        try {
            return genreDao.update(genre);
        } catch (SQLException e) {
            throw new ServiceException("Failed to update genre", e);
        }
    }
    
    @Override
    public boolean removeGenre(int genreId) throws ServiceException {
        try {
            return genreDao.delete(genreId);
        } catch (SQLException e) {
            throw new ServiceException("Failed to remove genre", e);
        }
    }
    
    @Override
    public List<Genre> getAllGenres() throws ServiceException {
        try {
            return genreDao.findAll();
        } catch (SQLException e) {
            throw new ServiceException("Failed to get all genres", e);
        }
    }
    
    @Override
    public Genre findByName(String name) throws ServiceException {
        try {
            return genreDao.findByName(name)
                .orElseThrow(() -> new ServiceException("Genre not found with name: " + name));
        } catch (SQLException e) {
            throw new ServiceException("Failed to find genre by name", e);
        }
    }
}
