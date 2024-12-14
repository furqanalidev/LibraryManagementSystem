package com.assignment.dao;

import com.assignment.data.Book;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Book entity operations.
 * Extends the generic Dao interface with Book-specific operations.
 *
 * @author MeHeR ALi
 */
public interface BookDao extends Dao<Book> {
    
    /**
     * Finds a book by its ISBN.
     *
     * @param isbn the ISBN to search for
     * @return the found book or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<Book> findByIsbn(String isbn) throws SQLException;
    
    /**
     * Finds all books by a specific author.
     *
     * @param author the author name to search for
     * @return list of books by the author
     * @throws SQLException if a database access error occurs
     */
    List<Book> findByAuthor(String author) throws SQLException;
    
    /**
     * Finds all books in a specific genre.
     *
     * @param genreId the ID of the genre to search for
     * @return list of books in the genre
     * @throws SQLException if a database access error occurs
     */
    List<Book> findByGenre(int genreId) throws SQLException;
    
    /**
     * Updates the available copies count for a book.
     *
     * @param bookId the ID of the book
     * @param count the new available copies count
     * @return true if update was successful
     * @throws SQLException if a database access error occurs
     */
    boolean updateAvailableCopies(int bookId, int count) throws SQLException;
}
