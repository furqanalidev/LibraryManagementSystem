package com.assignment.dao.mysql;

import com.assignment.dao.BookBorrowDao;
import com.assignment.data.Book;
import com.assignment.data.BookBorrow;
import com.assignment.data.Borrow;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import com.assignment.data.Staff;
import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the BookBorrowDao interface.
 *
 * @author MeHeR ALi
 */
public class MySqlBookBorrowDao implements BookBorrowDao {

    private static final String SELECT_BY_ID = 
        "SELECT * FROM BookBorrow WHERE id = ?";
    private static final String SELECT_ALL = 
        "SELECT * FROM BookBorrow";
    private static final String INSERT = 
        "INSERT INTO BookBorrow (bookId, date, status, staffId) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = 
        "UPDATE BookBorrow SET bookId = ?, date = ?, status = ?, staffId = ? WHERE id = ?";
    private static final String DELETE = 
        "DELETE FROM BookBorrow WHERE id = ?";
    private static final String SELECT_BY_STATUS = 
        "SELECT * FROM BookBorrow WHERE status = ?";
    private static final String SELECT_BY_BOOK = 
        "SELECT * FROM BookBorrow WHERE bookId = ?";
    private static final String UPDATE_STATUS = 
        "UPDATE BookBorrow SET status = ? WHERE id = ?";

    private final Connection connection;

    public MySqlBookBorrowDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<BookBorrow> findById(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractBookBorrowFromResultSet(rs));
            }
        }
        return Optional.empty();
    }

    @Override
    public List<BookBorrow> findAll() throws SQLException {
        List<BookBorrow> borrows = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(SELECT_ALL);
            while (rs.next()) {
                borrows.add(extractBookBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public BookBorrow save(BookBorrow borrow) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setBookBorrowParameters(stmt, borrow);
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                borrow.setId(generatedKeys.getInt(1));
            }
            return borrow;
        }
    }

    @Override
    public boolean update(BookBorrow borrow) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            setBookBorrowParameters(stmt, borrow);
            stmt.setInt(5, borrow.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<BookBorrow> findByStatus(Borrow.Status status) throws SQLException {
        List<BookBorrow> borrows = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_STATUS)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrows.add(extractBookBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public List<BookBorrow> findByBookId(int bookId) throws SQLException {
        List<BookBorrow> borrows = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_BY_BOOK)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrows.add(extractBookBorrowFromResultSet(rs));
            }
        }
        return borrows;
    }

    @Override
    public boolean updateStatus(int borrowId, Borrow.Status status) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_STATUS)) {
            stmt.setString(1, status.name());
            stmt.setInt(2, borrowId);
            return stmt.executeUpdate() > 0;
        }
    }
      private BookBorrow extractBookBorrowFromResultSet(ResultSet rs) throws SQLException {
          // Get Staff details
          String staffQuery = "SELECT * FROM Staff WHERE id = ?";
          try (PreparedStatement staffStmt = connection.prepareStatement(staffQuery)) {
              staffStmt.setInt(1, rs.getInt("staffId"));
              ResultSet staffRs = staffStmt.executeQuery();
              if (staffRs.next()) {
                  Staff staff = new Staff(
                      staffRs.getInt("id"),
                      staffRs.getString("firstName"),
                      staffRs.getString("lastName"),
                      staffRs.getLong("cnic"),
                      staffRs.getString("address"),
                      staffRs.getString("contact"),
                      staffRs.getString("email"),
                      Staff.Occupation.valueOf(staffRs.getString("occupation")),
                      staffRs.getTimestamp("created_at") != null ? 
                          staffRs.getTimestamp("created_at").toLocalDateTime() : null
                  );

                  // Get Book details
                  String bookQuery = "SELECT * FROM Book WHERE id = ?";
                  try (PreparedStatement bookStmt = connection.prepareStatement(bookQuery)) {
                      bookStmt.setInt(1, rs.getInt("bookId"));
                      ResultSet bookRs = bookStmt.executeQuery();
                      if (bookRs.next()) {
                          Book book = new Book(
                              bookRs.getInt("id"),
                              bookRs.getString("isbn"),
                              bookRs.getString("title"),
                              bookRs.getString("author"),
                              bookRs.getString("publisher"),
                              new Language(bookRs.getInt("language_id"), null),
                              Year.of(bookRs.getInt("year")),
                              bookRs.getInt("availableCopies"),
                              new Genre(bookRs.getInt("genre_id"), null),
                              bookRs.getBoolean("isBorrowable"),
                              bookRs.getTimestamp("created_at") != null ? 
                                  bookRs.getTimestamp("created_at").toLocalDateTime() : null,
                              bookRs.getTimestamp("updated_at") != null ? 
                                  bookRs.getTimestamp("updated_at").toLocalDateTime() : null
                          );

                          return new BookBorrow(
                              rs.getInt("id"),
                              rs.getTimestamp("date").toLocalDateTime(),
                              Borrow.Status.valueOf(rs.getString("status")),
                              book,
                              staff,
                              rs.getTimestamp("created_at") != null ? 
                                  rs.getTimestamp("created_at").toLocalDateTime() : null,
                              rs.getTimestamp("updated_at") != null ? 
                                  rs.getTimestamp("updated_at").toLocalDateTime() : null
                          );
                      }
                  }
              }
          }
          throw new SQLException("Required data not found for BookBorrow record");
      }

    private void setBookBorrowParameters(PreparedStatement stmt, BookBorrow borrow) throws SQLException {
        stmt.setInt(1, borrow.getId());
        stmt.setTimestamp(2, Timestamp.valueOf(borrow.getDate()));
        stmt.setString(3, borrow.getStatus().name());
        stmt.setInt(4, borrow.getStaff().getId());
    }
}
