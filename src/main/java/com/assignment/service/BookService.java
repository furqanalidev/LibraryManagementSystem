package com.assignment.service;

import com.assignment.data.Book;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import java.util.List;

/**
 * Service interface for managing book-related operations.
 *
 * @author MeHeR ALi
 */
public interface BookService {
    
    /**
     * Adds a new book to the library.
     *
     * @param book the book to add
     * @return the added book with generated ID
     * @throws ServiceException if operation fails
     */
    Book addBook(Book book) throws ServiceException;
    
    /**
     * Updates an existing book's information.
     *
     * @param book the book with updated information
     * @return true if update was successful
     * @throws ServiceException if operation fails
     */
    boolean updateBook(Book book) throws ServiceException;
    
    /**
     * Removes a book from the library.
     *
     * @param bookId the ID of the book to remove
     * @return true if removal was successful
     * @throws ServiceException if operation fails
     */
    boolean removeBook(int bookId) throws ServiceException;
    
    /**
     * Searches for books by title.
     *
     * @param title the title to search for
     * @return list of matching books
     * @throws ServiceException if operation fails
     */
    List<Book> searchByTitle(String title) throws ServiceException;
    
    /**
     * Finds books by author.
     *
     * @param author the author to search for
     * @return list of books by the author
     * @throws ServiceException if operation fails
     */
    List<Book> findByAuthor(String author) throws ServiceException;
    
    /**
     * Finds books by genre.
     *
     * @param genre the genre to search for
     * @return list of books in the genre
     * @throws ServiceException if operation fails
     */
    List<Book> findByGenre(Genre genre) throws ServiceException;
    
    /**
     * Finds books by language.
     *
     * @param language the language to search for
     * @return list of books in the language
     * @throws ServiceException if operation fails
     */
    List<Book> findByLanguage(Language language) throws ServiceException;
    
    /**
     * Gets all available books (copies > 0).
     *
     * @return list of available books
     * @throws ServiceException if operation fails
     */
    List<Book> getAvailableBooks() throws ServiceException;
}
