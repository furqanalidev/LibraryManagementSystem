package com.assignment.cli.handlers;

import com.assignment.service.*;
import com.assignment.data.*;
import java.util.Scanner;
import java.util.List;

public class MagazineOperationHandler {
    private final MagazineService magazineService;
    private final GenreService genreService;
    private final LanguageService languageService;
    private final Scanner scanner;

    public MagazineOperationHandler(ServiceFactory serviceFactory, Scanner scanner) {
        this.magazineService = serviceFactory.getMagazineService();
        this.genreService = serviceFactory.getGenreService();
        this.languageService = serviceFactory.getLanguageService();
        this.scanner = scanner;
    }

    public void handleMagazineOperations() {
        while (true) {
            System.out.println("\n=== Magazine Operations ===");
            System.out.println("1. Add New Magazine");
            System.out.println("2. Search Magazine");
            System.out.println("3. Update Magazine");
            System.out.println("4. Remove Magazine");
            System.out.println("0. Back");
    
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
    
            switch (choice) {
                case 1:
                    handleAddMagazine();
                    break;
                case 2:
                    handleSearchMagazine();
                    break;
                case 3:
                    handleUpdateMagazine();
                    break;
                case 4:
                    handleRemoveMagazine();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void handleRemoveMagazine() {
        try {
            System.out.print("Enter Magazine ID to remove: ");
            int magazineId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            if (magazineService.removeMagazine(magazineId)) {
                System.out.println("Magazine removed successfully");
            } else {
                System.out.println("Magazine removal failed");
            }
        } catch (ServiceException e) {
            System.out.println("Error removing magazine: " + e.getMessage());
        }
    }
    
    public void handleUpdateMagazine() {
        try {
            System.out.print("Enter Magazine ID to update: ");
            int magazineId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            // First find the existing magazine
            List<Magazine> magazines = magazineService.searchByTitle("");
            Magazine existingMagazine = magazines.stream()
                .filter(b -> b.getId() == magazineId)
                .findFirst()
                .orElseThrow(() -> new ServiceException("Magazine not found"));
            
            // Show current details and get updates
            System.out.println("\nCurrent Magazine Details:");
            displayMagazineDetails(existingMagazine);
            
            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("Title [" + existingMagazine.getTitle() + "]: ");
            String title = scanner.nextLine();
            title = title.isEmpty() ? existingMagazine.getTitle() : title;
            
            System.out.print("Available Copies [" + existingMagazine.getAvailableCopies() + "]: ");
            String copiesStr = scanner.nextLine();
            int copies = copiesStr.isEmpty() ? 
                existingMagazine.getAvailableCopies() : Integer.parseInt(copiesStr);
            
            Magazine updatedMagazine = new Magazine(
                magazineId,
                title,
                copies,
                existingMagazine.isBorrowable(),
                existingMagazine.getGenre(),
                existingMagazine.getLanguage()
            );
            
            if (magazineService.updateMagazine(updatedMagazine)) {
                System.out.println("Magazine updated successfully");
            } else {
                System.out.println("Failed to update magazine");
            }
            
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleAddMagazine() {
        try {
            System.out.println("\nEnter Magazine Details:");
            
            System.out.print("Title: ");
            String title = scanner.nextLine();
            
            // Display available genres
            List<Genre> genres = genreService.getAllGenres();
            System.out.println("\nAvailable Genres:");
            genres.forEach(g -> System.out.println(g.getId() + ". " + g.getName()));
            System.out.print("Select Genre ID: ");
            int genreId = scanner.nextInt();
            Genre genre = genres.stream()
                .filter(g -> g.getId() == genreId)
                .findFirst()
                .orElseThrow(() -> new ServiceException("Invalid genre ID"));
            
            // Display available languages
            List<Language> languages = languageService.getAllLanguages();
            System.out.println("\nAvailable Languages:");
            languages.forEach(l -> System.out.println(l.getId() + ". " + l.getName()));
            System.out.print("Select Language ID: ");
            int languageId = scanner.nextInt();
            Language language = languages.stream()
                .filter(l -> l.getId() == languageId)
                .findFirst()
                .orElseThrow(() -> new ServiceException("Invalid language ID"));
            
            System.out.print("Number of Copies: ");
            int copies = scanner.nextInt();
            
            System.out.print("Is Borrowable (true/false): ");
            boolean isBorrowable = scanner.nextBoolean();
            
            Magazine magazine = new Magazine(0, title, copies, isBorrowable, genre, language);
            Magazine savedMagazine = magazineService.addMagazine(magazine);
            
            System.out.println("Magazine added successfully with ID: " + savedMagazine.getId());
            
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleSearchMagazine() {
        try {
            System.out.println("\nSearch Options:");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Genre");
            System.out.println("3. Search by Language");
            System.out.println("4. View Available Magazines");
            
            System.out.print("Choose search option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            List<Magazine> results = null;
            
            switch (option) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    results = magazineService.searchByTitle(title);
                    break;
                    
                case 2:
                    List<Genre> genres = genreService.getAllGenres();
                    System.out.println("\nAvailable Genres:");
                    genres.forEach(g -> System.out.println(g.getId() + ". " + g.getName()));
                    System.out.print("Select Genre ID: ");
                    int genreId = scanner.nextInt();
                    Genre genre = genres.stream()
                        .filter(g -> g.getId() == genreId)
                        .findFirst()
                        .orElseThrow(() -> new ServiceException("Invalid genre ID"));
                    results = magazineService.findByGenre(genre);
                    break;
                    
                case 3:
                    List<Language> languages = languageService.getAllLanguages();
                    System.out.println("\nAvailable Languages:");
                    languages.forEach(l -> System.out.println(l.getId() + ". " + l.getName()));
                    System.out.print("Select Language ID: ");
                    int langId = scanner.nextInt();
                    Language language = languages.stream()
                        .filter(l -> l.getId() == langId)
                        .findFirst()
                        .orElseThrow(() -> new ServiceException("Invalid language ID"));
                    results = magazineService.findByLanguage(language);
                    break;
                    
                case 4:
                    results = magazineService.getAvailableMagazines();
                    break;
                    
                default:
                    System.out.println("Invalid option");
                    return;
            }
            
            if (results != null && !results.isEmpty()) {
                System.out.println("\nFound " + results.size() + " magazines:");
                results.forEach(this::displayMagazineDetails);
            } else {
                System.out.println("No magazines found");
            }
            
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayMagazineDetails(Magazine magazine) {
        System.out.println("\n----------------------------------------");
        System.out.println("ID: " + magazine.getId());
        System.out.println("Title: " + magazine.getTitle());
        System.out.println("Available Copies: " + magazine.getAvailableCopies());
        System.out.println("Genre: " + magazine.getGenre().getName());
        System.out.println("Language: " + magazine.getLanguage().getName());
        System.out.println("Borrowable: " + magazine.isBorrowable());
        System.out.println("----------------------------------------");
    }
}
