package com.assignment.service;

import com.assignment.dao.*;
import com.assignment.dao.mysql.*;
import com.assignment.service.impl.*;
import java.sql.Connection;

/**
 * Factory class for creating and managing service instances.
 *
 * @author MeHeR ALi
 */
public class ServiceFactory {
    
    private final DatabaseConnectionService dbService;
    private BookService bookService;
    private BorrowService borrowService;
    private FineService fineService;
    private UserService userService;
    private StaffService staffService;
    private MagazineService magazineService;
    private GenreService genreService;
    private LanguageService languageService;
    private UserActivityLogService activityLogService;
    private ReportService reportService;
    
    public ServiceFactory(String dbUrl, String dbUsername, String dbPassword) {
        this.dbService = new DatabaseConnectionServiceImpl(dbUrl, dbUsername, dbPassword);
        initializeServices();
    }
    
    private void initializeServices() {
        try {
            Connection conn = dbService.getConnection();
            
            // Initialize DAOs
            BookDao bookDao = new MySqlBookDao(conn);
            UserDao userDao = new MySqlUserDao(conn);
            StaffDao staffDao = new MySqlStaffDao(conn);
            MagazineDao magazineDao = new MySqlMagazineDao(conn);
            GenreDao genreDao = new MySqlGenreDao(conn);
            LanguageDao languageDao = new MySqlLanguageDao(conn);
            BookBorrowDao bookBorrowDao = new MySqlBookBorrowDao(conn);
            MagazineBorrowDao magazineBorrowDao = new MySqlMagazineBorrowDao(conn);
            UserBookBorrowDao userBookBorrowDao = new MySqlUserBookBorrowDao(conn);
            UserMagazineBorrowDao userMagazineBorrowDao = new MySqlUserMagazineBorrowDao(conn);
            BookFineDao bookFineDao = new MySqlBookFineDao(conn);
            MagazineFineDao magazineFineDao = new MySqlMagazineFineDao(conn);
            UserActivityLogDao activityLogDao = new MySqlUserActivityLogDao(conn);
            
            // Initialize Services
            bookService = new BookServiceImpl(bookDao);
            userService = new UserServiceImpl(userDao, activityLogDao);
            staffService = new StaffServiceImpl(staffDao);
            magazineService = new MagazineServiceImpl(magazineDao);
            genreService = new GenreServiceImpl(genreDao);
            languageService = new LanguageServiceImpl(languageDao);
            
            borrowService = new BorrowServiceImpl(
                bookBorrowDao, magazineBorrowDao,
                userBookBorrowDao, userMagazineBorrowDao,
                bookDao, magazineDao
            );
            
            fineService = new FineServiceImpl(
                bookFineDao, magazineFineDao,
                userBookBorrowDao, userMagazineBorrowDao
            );
            
            activityLogService = new UserActivityLogServiceImpl(activityLogDao);
            
            // reportService = new ReportServiceImpl(
            //     bookBorrowDao, magazineBorrowDao,
            //     activityLogDao, bookFineDao, magazineFineDao
            // );
            
        } catch (ServiceException e) {
            throw new RuntimeException("Failed to initialize services", e);
        }
    }
    
    public BookService getBookService() {
        return bookService;
    }
    
    public BorrowService getBorrowService() {
        return borrowService;
    }
    
    public FineService getFineService() {
        return fineService;
    }
    
    public UserService getUserService() {
        return userService;
    }
    
    public StaffService getStaffService() {
        return staffService;
    }
    
    public MagazineService getMagazineService() {
        return magazineService;
    }
    
    public GenreService getGenreService() {
        return genreService;
    }
    
    public LanguageService getLanguageService() {
        return languageService;
    }
    
    public UserActivityLogService getActivityLogService() {
        return activityLogService;
    }
    
    public ReportService getReportService() {
        return reportService;
    }
    
    public DatabaseConnectionService getDatabaseService() {
        return dbService;
    }
}
