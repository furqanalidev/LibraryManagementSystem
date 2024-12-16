package com.assignment.cli.handlers;

import com.assignment.service.*;
import com.assignment.data.*;
import java.util.Scanner;
import java.util.List;
import java.math.BigDecimal;

public class FineOperationHandler {
    private final FineService fineService;
    private final UserService userService;
    private final Scanner scanner;

    public FineOperationHandler(ServiceFactory serviceFactory, Scanner scanner) {
        this.fineService = serviceFactory.getFineService();
        this.userService = serviceFactory.getUserService();
        this.scanner = scanner;
    }

    public void handleFineOperations() {
        while (true) {
            System.out.println("\n=== Fine Management ===");
            System.out.println("1. Issue Book Fine");
            System.out.println("2. Issue Magazine Fine");
            System.out.println("3. Pay Book Fine");
            System.out.println("4. Pay Magazine Fine");
            System.out.println("5. View Unpaid Fines");
            System.out.println("0. Back");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    issueBookFine();
                    break;
                case 2:
                    issueMagazineFine();
                    break;
                case 3:
                    payBookFine();
                    break;
                case 4:
                    payMagazineFine();
                    break;
                case 5:
                    viewUnpaidFines();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void issueBookFine() {
        try {
            System.out.print("Enter user book borrow ID: ");
            int borrowId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter fine amount: ");
            BigDecimal amount = scanner.nextBigDecimal();
            scanner.nextLine();
            
            System.out.print("Enter reason for fine: ");
            String reason = scanner.nextLine();
            
            BookFine fine = fineService.issueBookFine(borrowId, amount, reason);
            System.out.println("Fine issued successfully with ID: " + fine.getId());
            
        } catch (ServiceException e) {
            System.out.println("Failed to issue fine: " + e.getMessage());
        }
    }

    private void issueMagazineFine() {
        try {
            System.out.print("Enter user magazine borrow ID: ");
            int borrowId = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter fine amount: ");
            BigDecimal amount = scanner.nextBigDecimal();
            scanner.nextLine();
            
            System.out.print("Enter reason for fine: ");
            String reason = scanner.nextLine();
            
            MagazineFine fine = fineService.issueMagazineFine(borrowId, amount, reason);
            System.out.println("Fine issued successfully with ID: " + fine.getId());
            
        } catch (ServiceException e) {
            System.out.println("Failed to issue fine: " + e.getMessage());
        }
    }

    private void payBookFine() {
        try {
            System.out.print("Enter fine ID to pay: ");
            int fineId = scanner.nextInt();
            
            if (fineService.payBookFine(fineId)) {
                System.out.println("Fine paid successfully");
            } else {
                System.out.println("Failed to process payment");
            }
            
        } catch (ServiceException e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }

    private void payMagazineFine() {
        try {
            System.out.print("Enter fine ID to pay: ");
            int fineId = scanner.nextInt();
            
            if (fineService.payMagazineFine(fineId)) {
                System.out.println("Fine paid successfully");
            } else {
                System.out.println("Failed to process payment");
            }
            
        } catch (ServiceException e) {
            System.out.println("Payment failed: " + e.getMessage());
        }
    }

    private void viewUnpaidFines() {
        try {
            System.out.print("Enter user email: ");
            String email = scanner.nextLine();
            
            User user = userService.findByEmail(email);
            List<Fine> unpaidFines = fineService.getUnpaidFines(user.getId());
            
            if (unpaidFines.isEmpty()) {
                System.out.println("No unpaid fines found");
                return;
            }
            
            System.out.println("\nUnpaid Fines:");
            unpaidFines.forEach(this::displayFineDetails);
            
        } catch (ServiceException e) {
            System.out.println("Failed to retrieve fines: " + e.getMessage());
        }
    }

    private void displayFineDetails(Fine fine) {
        System.out.println("\n----------------------------------------");
        System.out.println("Fine ID: " + fine.getId());
        System.out.println("Amount: $" + fine.getAmount());
        System.out.println("Status: " + fine.getStatus());
        System.out.println("Reason: " + fine.getReason());
        System.out.println("Issue Date: " + fine.getCreatedAt());
        System.out.println("----------------------------------------");
    }
}
