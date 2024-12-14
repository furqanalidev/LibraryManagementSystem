package com.assignment.data;

/**
 * Represents a user's magazine borrowing record in the library system.
 * Links users to their magazine borrowing transactions.
 * 
 * @author MeHeR ALi
 * @see UserBorrow
 */
public class UserMagazineBorrow extends UserBorrow {
    /**
     * Creates a new UserMagazineBorrow instance.
     *
     * @param id             unique identifier for this record
     * @param user           the user who borrowed the magazine
     * @param magazineBorrow the magazine borrowing transaction record
     */
    public UserMagazineBorrow(int id, User user, MagazineBorrow magazineBorrow) {
        super(id, user, magazineBorrow);
    }

    /**
     * @return the magazine borrowing transaction record
     */
    @Override
    public MagazineBorrow getBorrow() {
        return (MagazineBorrow) super.getBorrow();
    }
}
