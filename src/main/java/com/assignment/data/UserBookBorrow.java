package com.assignment.data;

/**
 * Represents a user's book borrowing record in the library system.
 * Links users to their book borrowing transactions.
 * 
 * @author MeHeR ALi
 * @see UserBorrow
 */
public class UserBookBorrow extends UserBorrow {
    /**
     * Creates a new UserBookBorrow instance.
     *
     * @param id         unique identifier for this record
     * @param user       the user who borrowed the book
     * @param bookBorrow the book borrowing transaction record
     */
    public UserBookBorrow(int id, User user, BookBorrow bookBorrow) {
        super(id, user, bookBorrow);
    }

    /**
     * @return the book borrowing transaction record
     */
    @Override
    public BookBorrow getBorrow() {
        return (BookBorrow) super.getBorrow();
    }
}
