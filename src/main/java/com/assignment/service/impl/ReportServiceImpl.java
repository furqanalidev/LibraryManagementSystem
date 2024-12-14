// package com.assignment.service.impl;

// import com.assignment.dao.*;
// import com.assignment.data.*;
// import com.assignment.service.ReportService;
// import com.assignment.service.ServiceException;
// import java.math.BigDecimal;
// import java.sql.SQLException;
// import java.time.LocalDateTime;
// import java.util.*;
// import java.util.stream.Collectors;

// /**
//  * Implementation of the ReportService interface.
//  *
//  * @author MeHeR ALi
//  */
// public class ReportServiceImpl implements ReportService {
    
//     private final BookBorrowDao bookBorrowDao;
//     private final MagazineBorrowDao magazineBorrowDao;
//     private final UserActivityLogDao activityLogDao;
//     private final BookFineDao bookFineDao;
//     private final MagazineFineDao magazineFineDao;
    
//     public ReportServiceImpl(BookBorrowDao bookBorrowDao, 
//                            MagazineBorrowDao magazineBorrowDao,
//                            UserActivityLogDao activityLogDao,
//                            BookFineDao bookFineDao,
//                            MagazineFineDao magazineFineDao) {
//         this.bookBorrowDao = bookBorrowDao;
//         this.magazineBorrowDao = magazineBorrowDao;
//         this.activityLogDao = activityLogDao;
//         this.bookFineDao = bookFineDao;
//         this.magazineFineDao = magazineFineDao;
//     }
    
//     @Override
//     public Map<String, Integer> getBorrowingStats(LocalDateTime startDate, LocalDateTime endDate) 
//             throws ServiceException {
//         try {
//             Map<String, Integer> stats = new HashMap<>();
            
//             int bookBorrows = bookBorrowDao.countBorrowsInDateRange(startDate, endDate);
//             int magazineBorrows = magazineBorrowDao.countBorrowsInDateRange(startDate, endDate);
            
//             stats.put("Books", bookBorrows);
//             stats.put("Magazines", magazineBorrows);
//             stats.put("Total", bookBorrows + magazineBorrows);
            
//             return stats;
//         } catch (SQLException e) {
//             throw new ServiceException("Failed to generate borrowing statistics", e);
//         }
//     }
    
//     @Override
//     public List<Map.Entry<Book, Integer>> getMostBorrowedBooks(int limit) throws ServiceException {
//         try {
//             return bookBorrowDao.getMostBorrowedBooks(limit);
//         } catch (SQLException e) {
//             throw new ServiceException("Failed to get most borrowed books", e);
//         }
//     }
    
//     @Override
//     public List<Map.Entry<User, Integer>> getMostActiveUsers(int limit) throws ServiceException {
//         try {
//             return activityLogDao.getMostActiveUsers(limit);
//         } catch (SQLException e) {
//             throw new ServiceException("Failed to get most active users", e);
//         }
//     }
    
//     @Override
//     public Map<String, BigDecimal> getFineStats(LocalDateTime startDate, LocalDateTime endDate) 
//             throws ServiceException {
//         try {
//             Map<String, BigDecimal> stats = new HashMap<>();
            
//             BigDecimal bookFinesCollected = bookFineDao.getTotalCollectedFines(startDate, endDate);
//             BigDecimal magazineFinesCollected = magazineFineDao.getTotalCollectedFines(startDate, endDate);
//             BigDecimal bookFinesPending = bookFineDao.getTotalPendingFines(startDate, endDate);
//             BigDecimal magazineFinesPending = magazineFineDao.getTotalPendingFines(startDate, endDate);
            
//             stats.put("CollectedFines", bookFinesCollected.add(magazineFinesCollected));
//             stats.put("PendingFines", bookFinesPending.add(magazineFinesPending));
            
//             return stats;
//         } catch (SQLException e) {
//             throw new ServiceException("Failed to generate fine statistics", e);
//         }
//     }
// }
