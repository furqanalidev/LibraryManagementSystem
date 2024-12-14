package com.assignment.service.impl;

import com.assignment.dao.BookDao;
import com.assignment.data.Book;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import com.assignment.service.BookService;
import com.assignment.service.ServiceException;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of the BookService interface.
 *
 * @author MeHeR ALi
 */
public class BookServiceImpl implements BookService {
    
    private final BookDao bookDao;
    
    /**
     * Constructs a new BookServiceImpl with the specified DAO.
     *
     * @param bookDao the book data access object to use
     */
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }
    
    @Override
    public Book addBook(Book book) throws ServiceException {
        try {
            return bookDao.save(book);
        } catch (SQLException e) {
            throw new ServiceException("Failed to add book", e);
        }
    }
    
    @Override
    public boolean updateBook(Book book) throws ServiceException {
        try {
            return bookDao.update(book);
        } catch (SQLException e) {
            throw new ServiceException("Failed to update book", e);
        }
    }
    
    @Override
    public boolean removeBook(int bookId) throws ServiceException {
        try {
            return bookDao.delete(bookId);
        } catch (SQLException e) {
            throw new ServiceException("Failed to remove book", e);
        }
    }
    
    @Override
    public List<Book> searchByTitle(String title) throws ServiceException {
        try {
            return bookDao.findByTitle(title);
        } catch (SQLException e) {
            throw new ServiceException("Failed to search books by title", e);
        }
    }
    
    @Override
    public List<Book> findByAuthor(String author) throws ServiceException {
        try {
            return bookDao.findByAuthor(author);
        } catch (SQLException e) {
            throw new ServiceException("Failed to find books by author", e);
        }
    }
    
    @Override
    public List<Book> findByGenre(Genre genre) throws ServiceException {
        try {
            return bookDao.findByGenre(genre);
        } catch (SQLException e) {
            throw new ServiceException("Failed to find books by genre", e);
        }
    }
    
    @Override
    public List<Book> findByLanguage(Language language) throws ServiceException {
        try {
            return bookDao.findByLanguage(language);
        } catch (SQLException e) {
            throw new ServiceException("Failed to find books by language", e);
        }
    }
    
    @Override
    public List<Book> getAvailableBooks() throws ServiceException {
        try {
            return bookDao.findAvailable();
        } catch (SQLException e) {
            throw new ServiceException("Failed to get available books", e);
        }
    }
}
