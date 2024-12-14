package com.assignment.service.impl;

import com.assignment.dao.*;
import com.assignment.data.*;
import com.assignment.service.FineService;
import com.assignment.service.ServiceException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the FineService interface.
 *
 * @author MeHeR ALi
 */
public class FineServiceImpl implements FineService {
    
    private final BookFineDao bookFineDao;
    private final MagazineFineDao magazineFineDao;
    private final UserBookBorrowDao userBookBorrowDao;
    private final UserMagazineBorrowDao userMagazineBorrowDao;
    
    public FineServiceImpl(BookFineDao bookFineDao, 
                         MagazineFineDao magazineFineDao,
                         UserBookBorrowDao userBookBorrowDao,
                         UserMagazineBorrowDao userMagazineBorrowDao) {
        this.bookFineDao = bookFineDao;
        this.magazineFineDao = magazineFineDao;
        this.userBookBorrowDao = userBookBorrowDao;
        this.userMagazineBorrowDao = userMagazineBorrowDao;
    }
    
    @Override
    public BookFine issueBookFine(int userBookBorrowId, BigDecimal amount, String reason) throws ServiceException {
        try {
            BookFine fine = new BookFine(0, amount, Fine.FineStatus.UNPAID, reason, userBookBorrowId);
            return bookFineDao.save(fine);
        } catch (SQLException e) {
            throw new ServiceException("Failed to issue book fine", e);
        }
    }
    
    @Override
    public MagazineFine issueMagazineFine(int userMagazineBorrowId, BigDecimal amount, String reason) throws ServiceException {
        try {
            MagazineFine fine = new MagazineFine(0, amount, Fine.FineStatus.UNPAID, reason, userMagazineBorrowId);
            return magazineFineDao.save(fine);
        } catch (SQLException e) {
            throw new ServiceException("Failed to issue magazine fine", e);
        }
    }
    
    @Override
    public boolean payBookFine(int fineId) throws ServiceException {
        try {
            BookFine fine = bookFineDao.findById(fineId)
                .orElseThrow(() -> new ServiceException("Fine record not found"));
                
            if (fine.getStatus() == Fine.FineStatus.PAID) {
                throw new ServiceException("Fine already paid");
            }
            
            fine = new BookFine(fine.getId(), fine.getAmount(), Fine.FineStatus.PAID, 
                              fine.getReason(), fine.getUserBookBorrowId());
            return bookFineDao.update(fine);
        } catch (SQLException e) {
            throw new ServiceException("Failed to process book fine payment", e);
        }
    }
    
    @Override
    public boolean payMagazineFine(int fineId) throws ServiceException {
        try {
            MagazineFine fine = magazineFineDao.findById(fineId)
                .orElseThrow(() -> new ServiceException("Fine record not found"));
                
            if (fine.getStatus() == Fine.FineStatus.PAID) {
                throw new ServiceException("Fine already paid");
            }
            
            fine = new MagazineFine(fine.getId(), fine.getAmount(), Fine.FineStatus.PAID, 
                                  fine.getReason(), fine.getUserMagazineBorrowId());
            return magazineFineDao.update(fine);
        } catch (SQLException e) {
            throw new ServiceException("Failed to process magazine fine payment", e);
        }
    }
    
    @Override
    public List<Fine> getUnpaidFines(int userId) throws ServiceException {
        try {
            List<Fine> unpaidFines = new ArrayList<>();
            
            // Get unpaid book fines
            List<UserBookBorrow> userBookBorrows = userBookBorrowDao.findByUserId(userId);
            for (UserBookBorrow borrow : userBookBorrows) {
                List<BookFine> bookFines = bookFineDao.findByUserBookBorrowId(borrow.getId());
                bookFines.stream()
                    .filter(fine -> fine.getStatus() == Fine.FineStatus.UNPAID)
                    .forEach(unpaidFines::add);
            }
            
            // Get unpaid magazine fines
            List<UserMagazineBorrow> userMagazineBorrows = userMagazineBorrowDao.findByUserId(userId);
            for (UserMagazineBorrow borrow : userMagazineBorrows) {
                List<MagazineFine> magazineFines = magazineFineDao.findByUserMagazineBorrowId(borrow.getId());
                magazineFines.stream()
                    .filter(fine -> fine.getStatus() == Fine.FineStatus.UNPAID)
                    .forEach(unpaidFines::add);
            }
            
            return unpaidFines;
        } catch (SQLException e) {
            throw new ServiceException("Failed to get unpaid fines", e);
        }
    }
}
