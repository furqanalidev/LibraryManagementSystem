package com.assignment.cli.handlers;

import com.assignment.service.*;
import com.assignment.data.*;
import java.util.Scanner;
import java.util.List;

public class BorrowOperationHandler {
    private final BorrowService borrowService;
    private final BookService bookService;
    private final UserService userService;
    private final StaffService staffService;
    private final MagazineService magazineService;
    private final Scanner scanner;

    public BorrowOperationHandler(ServiceFactory serviceFactory, Scanner scanner) {
        this.borrowService = serviceFactory.getBorrowService();
        this.bookService = serviceFactory.getBookService();
        this.userService = serviceFactory.getUserService();
        this.staffService = serviceFactory.getStaffService();
        this.magazineService = serviceFactory.getMagazineService();
        this.scanner = scanner;
    }

    public void handleBorrowOperations() {
        while (true) {
            System.out.println("\n=== Borrowing Operations ===");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Borrow Magazine");
            System.out.println("4. Return Magazine");
            System.out.println("5. View Current Borrowings");
            System.out.println("0. Back");
    
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
    
            switch (choice) {
                case 1:
                    handleBorrowBook();
                    break;
                case 2:
                    handleReturnBook();
                    break;
                case 3:
                    handleBorrowMagazine();
                    break;
                case 4:
                    handleReturnMagazine();
                    break;
                case 5:
                    viewCurrentBorrowings();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    

    public void handleBorrowBook() {
        try {
            // Get user
            System.out.print("Enter user email: ");
            String userEmail = scanner.nextLine();
            User user = userService.findByEmail(userEmail);

            // Show available books
            List<Book> availableBooks = bookService.getAvailableBooks();
            System.out.println("\nAvailable Books:");
            for (Book book : availableBooks) {
                System.out.println(book.getId() + ". " + book.getTitle() + 
                                 " (Copies: " + book.getAvailableCopies() + ")");
            }

            // Select book
            System.out.print("Enter Book ID to borrow: ");
            int bookId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            Book selectedBook = availableBooks.stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new ServiceException("Book not found or not available"));

            // Get staff processing the request
            System.out.print("Enter staff email: ");
            String staffEmail = scanner.nextLine();
            Staff staff = staffService.findByEmail(staffEmail);

            // Process borrow request
            BookBorrow borrow = borrowService.borrowBook(user, selectedBook, staff);
            System.out.println("Book borrowed successfully. Borrow ID: " + borrow.getId());

        } catch (ServiceException e) {
            System.out.println("Error processing borrow request: " + e.getMessage());
        }
    }

    public void handleBorrowMagazine() {
        try {
            // Get user
            System.out.print("Enter user email: ");
            String userEmail = scanner.nextLine();
            User user = userService.findByEmail(userEmail);

            // Show available magazines
            List<Magazine> availableMagazines = magazineService.getAvailableMagazines();
            System.out.println("\nAvailable Magazines:");
            for (Magazine magazine : availableMagazines) {
                System.out.println(magazine.getId() + ". " + magazine.getTitle() + 
                                 " (Copies: " + magazine.getAvailableCopies() + ")");
            }

            // Select magazine
            System.out.print("Enter Magazine ID to borrow: ");
            int magazineId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            Magazine selectedMagazine = availableMagazines.stream()
                .filter(b -> b.getId() == magazineId)
                .findFirst()
                .orElseThrow(() -> new ServiceException("Magazine not found or not available"));

            // Get staff processing the request
            System.out.print("Enter staff email: ");
            String staffEmail = scanner.nextLine();
            Staff staff = staffService.findByEmail(staffEmail);

            // Process borrow request
            MagazineBorrow borrow = borrowService.borrowMagazine(user, selectedMagazine, staff);
            System.out.println("Magazine borrowed successfully. Borrow ID: " + borrow.getId());

        } catch (ServiceException e) {
            System.out.println("Error processing borrow request: " + e.getMessage());
        }
    }

    public void handleReturnBook() {
        try {
            System.out.print("Enter borrow ID to return: ");
            int borrowId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (borrowService.returnBook(borrowId)) {
                System.out.println("Book returned successfully");
            } else {
                System.out.println("Failed to return book");
            }

        } catch (ServiceException e) {
            System.out.println("Error processing return: " + e.getMessage());
        }
    }

    public void handleReturnMagazine() {
        try {
            System.out.print("Enter borrow ID to return: ");
            int borrowId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (borrowService.returnMagazine(borrowId)) {
                System.out.println("Magazine returned successfully");
            } else {
                System.out.println("Failed to return magazine");
            }

        } catch (ServiceException e) {
            System.out.println("Error processing return: " + e.getMessage());
        }
    }

    public void viewCurrentBorrowings() {
        try {
            System.out.print("Enter user email: ");
            String userEmail = scanner.nextLine();
            User user = userService.findByEmail(userEmail);

            List<BookBorrow> activeBookBorrows = borrowService.getActiveBookBorrows(user.getId());
            
            if (activeBookBorrows.isEmpty()) {
                System.out.println("No active book borrowings found");
                return;
            }

            System.out.println("\nActive Book Borrowings:");
            for (BookBorrow borrow : activeBookBorrows) {
                displayBorrowDetails(borrow);
            }

        } catch (ServiceException e) {
            System.out.println("Error retrieving borrowings: " + e.getMessage());
        }
    }

    private void displayBorrowDetails(BookBorrow borrow) {
        System.out.println("\n----------------------------------------");
        System.out.println("Borrow ID: " + borrow.getId());
        System.out.println("Book: " + borrow.getBook().getTitle());
        System.out.println("Date: " + borrow.getDate());
        System.out.println("Status: " + borrow.getStatus());
        System.out.println("Processed by: " + borrow.getStaff().getFirstName() + 
                         " " + borrow.getStaff().getLastName());
        System.out.println("----------------------------------------");
    }
}
