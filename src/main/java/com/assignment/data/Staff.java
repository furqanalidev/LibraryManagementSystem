package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Represents a library staff member with defined roles.
 * This class extends Person to inherit personal information while adding
 * staff-specific functionality and role management.
 * 
 * @author MeHeR ALi
 * @see Person
 */
public class Staff extends Person {
    /**
     * Defines the possible roles a staff member can have in the library system.
     * Each role carries different responsibilities and access levels.
     */
    public enum Occupation {
        /** Manages library operations and has full system access */
        LIBRARIAN,
        /** Helps with day-to-day operations with limited access */
        ASSISTANT,
        /** System administrator with technical access rights */
        ADMIN
    }

    // Staff Information
    /** The role/position held by this staff member */
    private final Occupation occupation;
    
    /**
     * Creates a new Staff instance for database insertion.
     * The createdAt timestamp will be automatically set by the database.
     *
     * @param id         unique identifier for the staff member
     * @param firstName  staff member's legal first name
     * @param lastName   staff member's legal last name
     * @param cnic       unique national identity number
     * @param address    current residential address
     * @param contact    primary contact number
     * @param email      primary email address
     * @param occupation staff member's role in the library
     */
    public Staff(int id, String firstName, String lastName, Long cnic, 
                String address, String contact, String email, Occupation occupation) {
        this(id, firstName, lastName, cnic, address, contact, email, occupation, null);
    }

    /**
     * Creates a Staff instance from existing database record.
     * Used when retrieving records that already have a creation timestamp.
     *
     * @param id         unique identifier for the staff member
     * @param firstName  staff member's legal first name
     * @param lastName   staff member's legal last name
     * @param cnic       unique national identity number
     * @param address    current residential address
     * @param contact    primary contact number
     * @param email      primary email address
     * @param occupation staff member's role in the library
     * @param createdAt  timestamp when record was created
     */
    public Staff(int id, String firstName, String lastName, Long cnic, 
                String address, String contact, String email, Occupation occupation,
                LocalDateTime createdAt) {
        super(id, firstName, lastName, cnic, address, contact, email, createdAt);
        this.occupation = occupation;
    }

    /**
     * @return the role/position of this staff member
     */
    public Occupation getOccupation() {
        return occupation;
    }
}