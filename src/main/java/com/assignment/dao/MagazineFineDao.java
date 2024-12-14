package com.assignment.dao;

import com.assignment.data.MagazineFine;
import java.sql.SQLException;
import java.util.List;

public interface MagazineFineDao extends FineDao<MagazineFine> {
    List<MagazineFine> findByUserMagazineBorrowId(int userMagazineBorrowId) throws SQLException;
}
