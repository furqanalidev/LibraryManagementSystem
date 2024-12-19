package com.assignment.cli.handlers;

import com.assignment.service.*;
import com.assignment.data.*;
import java.util.Scanner;
import java.util.List;

public class StaffOperationHandler {
    private final StaffService staffService;
    private final Scanner scanner;

    public StaffOperationHandler(ServiceFactory serviceFactory, Scanner scanner) {
        this.staffService = serviceFactory.getStaffService();
        this.scanner = scanner;
    }

    public void handleStaffOperations() {
        while (true) {
            System.out.println("\n=== Staff Operations ===");
            System.out.println("1. Register New Staff");
            System.out.println("2. Update Staff");
            System.out.println("3. Search Staff");
            System.out.println("4. View Staff by Occupation");
            System.out.println("0. Back");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    registerStaff();
                    break;
                case 2:
                    updateStaff();
                    break;
                case 3:
                    searchStaff();
                    break;
                case 4:
                    viewStaffByOccupation();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void registerStaff() {
        try {
            System.out.println("\nEnter Staff Details:");

            System.out.println("Username: ");
            String username = scanner.nextLine();
            
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
            
            System.out.println("\nSelect Occupation:");
            System.out.println("1. LIBRARIAN");
            System.out.println("2. ASSISTANT");
            System.out.println("3. ADMIN");
            System.out.print("Choice: ");
            int occChoice = scanner.nextInt();
            
            Staff.Occupation occupation;
            switch (occChoice) {
                case 1:
                    occupation = Staff.Occupation.LIBRARIAN;
                    break;
                case 2:
                    occupation = Staff.Occupation.ASSISTANT;
                    break;
                case 3:
                    occupation = Staff.Occupation.ADMIN;
                    break;
                default:
                    throw new ServiceException("Invalid occupation choice");
            }
            
            Staff staff = new Staff(0,username, firstName, lastName, cnic, address, 
                                  contact, email, occupation);
            
            Staff savedStaff = staffService.registerStaff(staff);
            System.out.println("Staff registered successfully with ID: " + savedStaff.getId());
            
        } catch (ServiceException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void updateStaff() {
        try {
            System.out.print("Enter staff email to update: ");
            String email = scanner.nextLine();
            
            Staff existingStaff = staffService.findByEmail(email);
            System.out.println("\nCurrent Staff Details:");
            displayStaffDetails(existingStaff);
            
            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("Address [" + existingStaff.getAddress() + "]: ");
            String address = scanner.nextLine();
            address = address.isEmpty() ? existingStaff.getAddress() : address;
            
            System.out.print("Contact [" + existingStaff.getContact() + "]: ");
            String contact = scanner.nextLine();
            contact = contact.isEmpty() ? existingStaff.getContact() : contact;
            
            Staff updatedStaff = new Staff(
                existingStaff.getId(),
                existingStaff.getUsername(),
                existingStaff.getFirstName(),
                existingStaff.getLastName(),
                existingStaff.getCnic(),
                address,
                contact,
                existingStaff.getEmail(),
                existingStaff.getOccupation()
            );
            
            if (staffService.updateStaff(updatedStaff)) {
                System.out.println("Staff updated successfully");
            }
            
        } catch (ServiceException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    private void searchStaff() {
        try {
            System.out.print("Enter staff email: ");
            String email = scanner.nextLine();
            
            Staff staff = staffService.findByEmail(email);
            displayStaffDetails(staff);
            
        } catch (ServiceException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }

    private void viewStaffByOccupation() {
        try {
            System.out.println("\nSelect Occupation:");
            System.out.println("1. LIBRARIAN");
            System.out.println("2. ASSISTANT");
            System.out.println("3. ADMIN");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            
            Staff.Occupation occupation;
            switch (choice) {
                case 1:
                    occupation = Staff.Occupation.LIBRARIAN;
                    break;
                case 2:
                    occupation = Staff.Occupation.ASSISTANT;
                    break;
                case 3:
                    occupation = Staff.Occupation.ADMIN;
                    break;
                default:
                    throw new ServiceException("Invalid occupation choice");
            }
            
            List<Staff> staffList = staffService.findByOccupation(occupation);
            
            if (staffList.isEmpty()) {
                System.out.println("No staff found with selected occupation");
                return;
            }
            
            System.out.println("\nStaff List:");
            staffList.forEach(this::displayStaffDetails);
            
        } catch (ServiceException e) {
            System.out.println("Failed to retrieve staff list: " + e.getMessage());
        }
    }

    private void displayStaffDetails(Staff staff) {
        System.out.println("\n----------------------------------------");
        System.out.println("ID: " + staff.getId());
        System.out.println("Name: " + staff.getFirstName() + " " + staff.getLastName());
        System.out.println("CNIC: " + staff.getCnic());
        System.out.println("Email: " + staff.getEmail());
        System.out.println("Contact: " + staff.getContact());
        System.out.println("Address: " + staff.getAddress());
        System.out.println("Occupation: " + staff.getOccupation());
        System.out.println("----------------------------------------");
    }
}
