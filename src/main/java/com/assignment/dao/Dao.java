package com.assignment.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Generic Data Access Object (DAO) interface defining standard CRUD operations.
 *
 * @param <T> the type of entity this DAO handles
 * @author MeHeR ALi
 */
public interface Dao<T> {
    
    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return an Optional containing the found entity or empty if not found
     * @throws SQLException if a database access error occurs
     */
    Optional<T> findById(int id) throws SQLException;
    
    /**
     * Retrieves all entities.
     *
     * @return list of all entities
     * @throws SQLException if a database access error occurs
     */
    List<T> findAll() throws SQLException;
    
    /**
     * Saves a new entity.
     *
     * @param entity the entity to save
     * @return the saved entity with generated ID
     * @throws SQLException if a database access error occurs
     */
    T save(T entity) throws SQLException;
    
    /**
     * Updates an existing entity.
     *
     * @param entity the entity to update
     * @return true if update was successful
     * @throws SQLException if a database access error occurs
     */
    boolean update(T entity) throws SQLException;
    
    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to delete
     * @return true if deletion was successful
     * @throws SQLException if a database access error occurs
     */
    boolean delete(int id) throws SQLException;
}
