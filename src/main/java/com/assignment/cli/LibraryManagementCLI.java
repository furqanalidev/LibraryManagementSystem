package com.assignment.cli;

import com.assignment.service.ServiceFactory;
import com.assignment.cli.handlers.*;
import java.util.Scanner;

public class LibraryManagementCLI {
    private final Scanner scanner;
    private final ServiceFactory serviceFactory;
    private final BookOperationHandler bookHandler;
    private final MagazineOperationHandler magazineHandler;
    private final UserOperationHandler userHandler;
    private final StaffOperationHandler staffHandler;
    private final BorrowOperationHandler borrowHandler;
    private final FineOperationHandler fineHandler;

    public LibraryManagementCLI() {
        this.scanner = new Scanner(System.in);
        String url = "jdbc:mysql://localhost:3307/library";
        String username = "root";
        String password = "rootpass";
        this.serviceFactory = new ServiceFactory(url, username, password);
        
        // Initialize handlers
        this.bookHandler = new BookOperationHandler(serviceFactory, scanner);
        this.magazineHandler = new MagazineOperationHandler(serviceFactory, scanner);
        this.userHandler = new UserOperationHandler(serviceFactory, scanner);
        this.staffHandler = new StaffOperationHandler(serviceFactory, scanner);
        this.borrowHandler = new BorrowOperationHandler(serviceFactory, scanner);
        this.fineHandler = new FineOperationHandler(serviceFactory, scanner);
    }

    public void start() {
        System.out.println("Welcome to Library Management System");
        
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    bookHandler.handleBookOperations();
                    break;
                case 2:
                    magazineHandler.handleMagazineOperations();
                    break;
                case 3:
                    userHandler.handleUserOperations();
                    break;
                case 4:
                    staffHandler.handleStaffOperations();
                    break;
                case 5:
                    borrowHandler.handleBorrowOperations();
                    break;
                case 6:
                    fineHandler.handleFineOperations();
                    break;
                case 0:
                    shutdown();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Book Management");
        System.out.println("2. Magazine Management");
        System.out.println("3. User Management");
        System.out.println("4. Staff Management");
        System.out.println("5. Borrowing Operations");
        System.out.println("6. Fine Management");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void shutdown() {
        try {
            System.out.println("Closing database connections...");
            serviceFactory.getDatabaseService().closeConnections();
            System.out.println("Thank you for using Library Management System");
        } catch (Exception e) {
            System.out.println("Error during shutdown: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}