package com.assignment.dao;

import com.assignment.data.MagazineBorrow;
import com.assignment.data.Borrow;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for MagazineBorrow entity operations.
 *
 * @author MeHeR ALi
 */
public interface MagazineBorrowDao extends Dao<MagazineBorrow> {
    
    /**
     * Finds all borrows by their current status.
     *
     * @param status the status to search for
     * @return list of magazine borrows with the specified status
     * @throws SQLException if a database access error occurs
     */
    List<MagazineBorrow> findByStatus(Borrow.Status status) throws SQLException;
    
    /**
     * Finds all borrows for a specific magazine.
     *
     * @param magazineId the ID of the magazine
     * @return list of magazine borrows for the specified magazine
     * @throws SQLException if a database access error occurs
     */
    List<MagazineBorrow> findByMagazineId(int magazineId) throws SQLException;
    
    /**
     * Updates the status of a magazine borrow.
     *
     * @param borrowId the ID of the borrow record
     * @param status the new status
     * @return true if update was successful
     * @throws SQLException if a database access error occurs
     */
    boolean updateStatus(int borrowId, Borrow.Status status) throws SQLException;
}
