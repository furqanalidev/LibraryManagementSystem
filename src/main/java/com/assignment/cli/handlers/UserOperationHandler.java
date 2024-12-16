package com.assignment.cli.handlers;

import com.assignment.service.*;
import com.assignment.data.*;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDateTime;

public class UserOperationHandler {
    private final UserService userService;
    private final UserActivityLogService activityLogService;
    private final Scanner scanner;

    public UserOperationHandler(ServiceFactory serviceFactory, Scanner scanner) {
        this.userService = serviceFactory.getUserService();
        this.activityLogService = serviceFactory.getActivityLogService();
        this.scanner = scanner;
    }

    public void handleUserOperations() {
        while (true) {
            System.out.println("\n=== User Operations ===");
            System.out.println("1. Register New User");
            System.out.println("2. Update User");
            System.out.println("3. Search User");
            System.out.println("4. View User Activity");
            System.out.println("5. View Activity By Date Range");
            System.out.println("0. Back");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    updateUser();
                    break;
                case 3:
                    searchUser();
                    break;
                case 4:
                    viewUserActivity();
                    break;
                case 5:
                    viewActivityByDateRange();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void registerUser() {
        try {
            System.out.println("\nEnter User Details:");
            
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();
            
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();
            
            System.out.print("CNIC: ");
            long cnic = scanner.nextLong();
            scanner.nextLine();
            
            System.out.print("Address: ");
            String address = scanner.nextLine();
            
            System.out.print("Contact: ");
            String contact = scanner.nextLine();
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            
            User user = new User(0, firstName, lastName, cnic, address, contact, 
                               email, 0, 5, 3); // Default limits
            
            User savedUser = userService.registerUser(user);
            System.out.println("User registered successfully with ID: " + savedUser.getId());
            
        } catch (ServiceException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void updateUser() {
        try {
            System.out.print("Enter user email to update: ");
            String email = scanner.nextLine();
            
            User existingUser = userService.findByEmail(email);
            System.out.println("\nCurrent User Details:");
            displayUserDetails(existingUser);
            
            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("Address [" + existingUser.getAddress() + "]: ");
            String address = scanner.nextLine();
            address = address.isEmpty() ? existingUser.getAddress() : address;
            
            System.out.print("Contact [" + existingUser.getContact() + "]: ");
            String contact = scanner.nextLine();
            contact = contact.isEmpty() ? existingUser.getContact() : contact;
            
            User updatedUser = new User(
                existingUser.getId(),
                existingUser.getFirstName(),
                existingUser.getLastName(),
                existingUser.getCnic(),
                address,
                contact,
                existingUser.getEmail(),
                existingUser.getBorrowings(),
                existingUser.getBookBorrowLimit(),
                existingUser.getMagazineBorrowLimit()
            );
            
            if (userService.updateUser(updatedUser)) {
                System.out.println("User updated successfully");
            }
            
        } catch (ServiceException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    private void searchUser() {
        try {
            System.out.print("Enter email or CNIC: ");
            String search = scanner.nextLine();
            
            User user;
            if (search.contains("@")) {
                user = userService.findByEmail(search);
            } else {
                user = userService.findByCnic(Long.parseLong(search));
            }
            
            displayUserDetails(user);
            
        } catch (ServiceException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    private void viewUserActivity() {
        try {
            System.out.print("Enter user email: ");
            String email = scanner.nextLine();
            
            User user = userService.findByEmail(email);
            List<UserActivityLog> activities = userService.getUserActivity(user.getId());
            
            if (activities.isEmpty()) {
                System.out.println("No activity found");
                return;
            }
            
            System.out.println("\nActivity Log:");
            activities.forEach(this::displayActivityLog);
            
        } catch (ServiceException e) {
            System.out.println("Failed to retrieve activity: " + e.getMessage());
        }
    }

    private void viewActivityByDateRange() {
        try {
            System.out.print("Enter user email: ");
            String email = scanner.nextLine();
            User user = userService.findByEmail(email);
            
            System.out.println("Enter start date (YYYY-MM-DD): ");
            String startDate = scanner.nextLine() + " 00:00:00";
            
            System.out.println("Enter end date (YYYY-MM-DD): ");
            String endDate = scanner.nextLine() + " 23:59:59";
            
            LocalDateTime start = LocalDateTime.parse(startDate.replace(" ", "T"));
            LocalDateTime end = LocalDateTime.parse(endDate.replace(" ", "T"));
            
            List<UserActivityLog> activities = activityLogService.getActivitiesByDateRange(
                user.getId(), start, end);
            
            if (activities.isEmpty()) {
                System.out.println("No activity found in date range");
                return;
            }
            
            System.out.println("\nActivity Log:");
            activities.forEach(this::displayActivityLog);
            
        } catch (ServiceException e) {
            System.out.println("Failed to retrieve activity: " + e.getMessage());
        }
    }

    private void displayUserDetails(User user) {
        System.out.println("\n----------------------------------------");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("CNIC: " + user.getCnic());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Contact: " + user.getContact());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Current Borrowings: " + user.getBorrowings());
        System.out.println("Book Borrow Limit: " + user.getBookBorrowLimit());
        System.out.println("Magazine Borrow Limit: " + user.getMagazineBorrowLimit());
        System.out.println("----------------------------------------");
    }

    private void displayActivityLog(UserActivityLog log) {
        System.out.println("\n----------------------------------------");
        System.out.println("Date: " + log.getDate());
        System.out.println("Action: " + log.getAction());
        System.out.println("Reference Type: " + log.getReferenceType());
        System.out.println("Reference ID: " + log.getReferenceId());
        System.out.println("Details: " + log.getDetails());
        System.out.println("----------------------------------------");
    }
}
