package com.assignment.data;

/**
 * Abstract base class for user borrowing records in the library system.
 * Links users to their borrowing transactions.
 * 
 * @author MeHeR ALi
 * @see GeneralEntity
 */
public abstract class UserBorrow extends GeneralEntity {
    /** The user who borrowed the item */
    private final User user;
    /** The borrowing transaction record */
    private final Borrow borrow;

    /**
     * Creates a new UserBorrow instance.
     *
     * @param id     unique identifier for this record
     * @param user   the user who borrowed the item
     * @param borrow the borrowing transaction record
     */
    public UserBorrow(int id, User user, Borrow borrow) {
        super(id);
        this.user = user;
        this.borrow = borrow;
    }

    /**
     * @return the user who borrowed the item
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the borrowing transaction record
     */
    public Borrow getBorrow() {
        return borrow;
    }
}
