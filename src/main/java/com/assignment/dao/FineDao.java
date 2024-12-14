package com.assignment.dao;

import com.assignment.data.Fine;
import java.sql.SQLException;
import java.util.List;

public interface FineDao<T extends Fine> extends Dao<T> {
    
    List<T> findByStatus(Fine.FineStatus status) throws SQLException;
    
    boolean updateStatus(int fineId, Fine.FineStatus status) throws SQLException;
}
