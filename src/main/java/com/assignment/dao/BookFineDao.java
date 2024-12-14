
package com.assignment.dao;

import com.assignment.data.BookFine;
import java.sql.SQLException;
import java.util.List;

public interface BookFineDao extends FineDao<BookFine> {
    List<BookFine> findByUserBookBorrowId(int userBookBorrowId) throws SQLException;
}
