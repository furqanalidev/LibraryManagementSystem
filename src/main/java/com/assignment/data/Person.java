package com.assignment.data;

import java.time.LocalDateTime;

/**
 * Represents a person in the system with basic personal information.
 * This class serves as a base for all person-related entities and provides
 * core functionality for managing personal and contact information.
 * 
 * @author MeHeR ALi
 * @see GeneralEntity
 */
public class Person extends GeneralEntity {
    // Personal Information
    /** First name of the person - cannot be modified after creation */
    private final String firstName;
    /** Last name of the person - cannot be modified after creation */
    private final String lastName;
    /** CNIC (Computerized National Identity Card) number - unique identifier */
    private final Long cnic;
    
    // Contact Information
    /** Residential or business address */
    private final String address;
    /** Primary contact number */
    private final String contact;
    /** Primary email address */
    private final String email;
    
    // Metadata
    /** Record creation timestamp - automatically set by database */
    private final LocalDateTime createdAt;
    
    /**
     * Creates a new Person instance for database insertion.
     * The createdAt timestamp will be automatically set by the database.
     *
     * @param id        unique identifier for the person
     * @param firstName person's legal first name
     * @param lastName  person's legal last name
     * @param cnic      unique national identity number
     * @param address   current residential address
     * @param contact   primary contact number
     * @param email     primary email address
     */
    public Person(int id, String firstName, String lastName, Long cnic, 
                 String address, String contact, String email) {
        this(id, firstName, lastName, cnic, address, contact, email, null);
    }

    /**
     * Creates a Person instance from existing database record.
     * Used when retrieving records that already have a creation timestamp.
     *
     * @param id        unique identifier for the person
     * @param firstName person's legal first name
     * @param lastName  person's legal last name
     * @param cnic      unique national identity number
     * @param address   current residential address
     * @param contact   primary contact number
     * @param email     primary email address
     * @param createdAt timestamp when record was created
     */
    public Person(int id, String firstName, String lastName, Long cnic, 
                 String address, String contact, String email, LocalDateTime createdAt) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnic = cnic;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.createdAt = createdAt;
    }

    /**
     * @return person's legal first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return person's legal last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return person's CNIC (Computerized National Identity Card) number
     */
    public Long getCnic() {
        return cnic;
    }

    /**
     * @return person's current residential address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return person's primary contact number
     */
    public String getContact() {
        return contact;
    }

    /**
     * @return person's primary email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return timestamp when this record was created in the database
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}