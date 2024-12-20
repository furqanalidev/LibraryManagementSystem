package com.assignment.service.impl;

import com.assignment.dao.*;
import com.assignment.data.*;
import com.assignment.service.BorrowService;
import com.assignment.service.ServiceException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the BorrowService interface.
 *
 * @author MeHeR ALi
 */
public class BorrowServiceImpl implements BorrowService {
    
    private final BookBorrowDao bookBorrowDao;
    private final MagazineBorrowDao magazineBorrowDao;
    private final UserBookBorrowDao userBookBorrowDao;
    private final UserMagazineBorrowDao userMagazineBorrowDao;
    private final BookDao bookDao;
    private final MagazineDao magazineDao;
    
    public BorrowServiceImpl(BookBorrowDao bookBorrowDao, 
                           MagazineBorrowDao magazineBorrowDao,
                           UserBookBorrowDao userBookBorrowDao,
                           UserMagazineBorrowDao userMagazineBorrowDao,
                           BookDao bookDao,
                           MagazineDao magazineDao) {
        this.bookBorrowDao = bookBorrowDao;
        this.magazineBorrowDao = magazineBorrowDao;
        this.userBookBorrowDao = userBookBorrowDao;
        this.userMagazineBorrowDao = userMagazineBorrowDao;
        this.bookDao = bookDao;
        this.magazineDao = magazineDao;
    }
    
    

    public BorrowServiceImpl(BookBorrowDao bookBorrowDao, UserBookBorrowDao userBookBorrowDao, BookDao bookDao) {
        this.bookBorrowDao = bookBorrowDao;
        this.userBookBorrowDao = userBookBorrowDao;
        this.bookDao = bookDao;
        this.magazineBorrowDao = null;
        this.magazineDao = null;
        this.userMagazineBorrowDao = null;
    }



    @Override
    public BookBorrow borrowBook(User user, Book book, Staff staff) throws ServiceException {
        try {
            if (book.getAvailableCopies() <= 0) {
                throw new ServiceException("No copies available for borrowing");
            }
            
            BookBorrow borrow = new BookBorrow(0, LocalDateTime.now(), 
                Borrow.Status.BORROWED, book, staff);
            BookBorrow savedBorrow = bookBorrowDao.save(borrow);
            
            UserBookBorrow userBorrow = new UserBookBorrow(0, user, savedBorrow);
            userBookBorrowDao.save(userBorrow);
            
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookDao.updateAvailableCopies(book.getId(), book.getAvailableCopies());
            
            return savedBorrow;
        } catch (SQLException e) {
            throw new ServiceException("Failed to process book borrowing", e);
        }
    }
    
    @Override
    public MagazineBorrow borrowMagazine(User user, Magazine magazine, Staff staff) throws ServiceException {
        try {
            if (magazine.getAvailableCopies() <= 0) {
                throw new ServiceException("No copies available for borrowing");
            }
            
            MagazineBorrow borrow = new MagazineBorrow(0, LocalDateTime.now(), 
                Borrow.Status.BORROWED, magazine, staff);
            MagazineBorrow savedBorrow = magazineBorrowDao.save(borrow);
            
            UserMagazineBorrow userBorrow = new UserMagazineBorrow(0, user, savedBorrow);
            userMagazineBorrowDao.save(userBorrow);
            
            magazine.setAvailableCopies(magazine.getAvailableCopies() - 1);
            magazineDao.updateAvailableCopies(magazine.getId(), magazine.getAvailableCopies());
            
            return savedBorrow;
        } catch (SQLException e) {
            throw new ServiceException("Failed to process magazine borrowing", e);
        }
    }
    
    @Override
    public boolean returnBook(int borrowId) throws ServiceException {
        try {
            BookBorrow borrow = bookBorrowDao.findById(borrowId)
                .orElseThrow(() -> new ServiceException("Borrow record not found"));
                
            if (borrow.getStatus() == Borrow.Status.RETURNED) {
                throw new ServiceException("Book already returned");
            }
            
            Book book = borrow.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookDao.updateAvailableCopies(book.getId(), book.getAvailableCopies());
            
            return bookBorrowDao.updateStatus(borrowId, Borrow.Status.RETURNED);
        } catch (SQLException e) {
            throw new ServiceException("Failed to process book return", e);
        }
    }
    
    @Override
    public boolean returnMagazine(int borrowId) throws ServiceException {
        try {
            MagazineBorrow borrow = magazineBorrowDao.findById(borrowId)
                .orElseThrow(() -> new ServiceException("Borrow record not found"));
                
            if (borrow.getStatus() == Borrow.Status.RETURNED) {
                throw new ServiceException("Magazine already returned");
            }
            
            Magazine magazine = borrow.getMagazine();
            magazine.setAvailableCopies(magazine.getAvailableCopies() + 1);
            magazineDao.updateAvailableCopies(magazine.getId(), magazine.getAvailableCopies());
            
            return magazineBorrowDao.updateStatus(borrowId, Borrow.Status.RETURNED);
        } catch (SQLException e) {
            throw new ServiceException("Failed to process magazine return", e);
        }
    }
    
    @Override
    public List<BookBorrow> getActiveBookBorrows(int userId) throws ServiceException {
        try {
            List<UserBookBorrow> userBorrows = userBookBorrowDao.findByUserId(userId);
            return userBorrows.stream()
                .map(UserBookBorrow::getBorrow)
                .filter(borrow -> borrow.getStatus() == Borrow.Status.BORROWED)
                .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new ServiceException("Failed to get active book borrows", e);
        }
    }
    
    @Override
    public List<MagazineBorrow> getActiveMagazineBorrows(int userId) throws ServiceException {
        try {
            List<UserMagazineBorrow> userBorrows = userMagazineBorrowDao.findByUserId(userId);
            return userBorrows.stream()
                .map(UserMagazineBorrow::getBorrow)
                .filter(borrow -> borrow.getStatus() == Borrow.Status.BORROWED)
                .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new ServiceException("Failed to get active magazine borrows", e);
        }
    }
}
