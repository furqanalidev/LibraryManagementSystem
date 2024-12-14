package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Represents a user activity log entry in the library system.
 * This class extends GeneralEntity to inherit ID functionality.
 * 
 * @author MeHeR ALi
 * @see GeneralEntity
 */
public class UserActivityLog extends GeneralEntity {
    
    /** The user whose activity is being logged */
    private final User user;
    /** The type of action performed */
    private final Action action;
    /** ID of the referenced entity (book/magazine) */
    private final int referenceId;
    /** Type of the referenced entity */
    private final ReferenceType referenceType;
    /** Date and time of the activity */
    private final LocalDateTime date;
    /** Additional details about the activity */
    private final String details;

    /**
     * Possible actions that can be logged
     */
    public enum Action {
        BORROW,
        RETURN,
        FINE_PAID,
        FINE_ISSUED
    }

    /**
     * Types of entities that can be referenced
     */
    public enum ReferenceType {
        BOOK,
        MAGAZINE
    }

    /**
     * Creates a new UserActivityLog instance.
     *
     * @param id the unique identifier
     * @param user the user performing the action
     * @param action the type of action
     * @param referenceId ID of the referenced entity
     * @param referenceType type of the referenced entity
     * @param date date and time of the activity
     * @param details additional activity details
     */
    public UserActivityLog(int id, User user, Action action, int referenceId, 
                         ReferenceType referenceType, LocalDateTime date, String details) {
        super(id);
        this.user = user;
        this.action = action;
        this.referenceId = referenceId;
        this.referenceType = referenceType;
        this.date = date;
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public Action getAction() {
        return action;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDetails() {
        return details;
    }
}
