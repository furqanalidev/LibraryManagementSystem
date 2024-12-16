package com.assignment.cli.handlers;

import com.assignment.service.*;
import com.assignment.data.*;
import java.time.Year;
import java.util.Scanner;
import java.util.List;

public class BookOperationHandler {
    private final BookService bookService;
    private final GenreService genreService;
    private final LanguageService languageService;
    private final Scanner scanner;

    public BookOperationHandler(ServiceFactory serviceFactory, Scanner scanner) {
        this.bookService = serviceFactory.getBookService();
        this.genreService = serviceFactory.getGenreService();
        this.languageService = serviceFactory.getLanguageService();
        this.scanner = scanner;
    }

    public void handleBookOperations() {
        while (true) {
            System.out.println("\n=== Book Operations ===");
            System.out.println("1. Add New Book");
            System.out.println("2. Update Book");
            System.out.println("3. Search Book");
            System.out.println("4. Remove Book");
            System.out.println("0. Back");
    
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
    
            switch (choice) {
                case 1:
                    handleAddBook();
                    break;
                case 2:
                    handleUpdateBook();
                    break;
                case 3:
                    handleSearchBook();
                    break;
                case 4:
                    handleRemoveBook();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void handleRemoveBook() {
        try {
            System.out.print("Enter Book ID to remove: ");
            int bookId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            if (bookService.removeBook(bookId)) {
                System.out.println("Book removed successfully");
            } else {
                System.out.println("Book removal failed");
            }
        } catch (ServiceException e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }
    
    

    public void handleAddBook() {
        try {
            System.out.println("\nEnter Book Details:");
            
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();
            
            System.out.print("Title: ");
            String title = scanner.nextLine();
            
            System.out.print("Author: ");
            String author = scanner.nextLine();
            
            System.out.print("Publisher: ");
            String publisher = scanner.nextLine();
            
            // Display available genres
            List<Genre> genres = genreService.getAllGenres();
            System.out.println("\nAvailable Genres:");
            for (Genre g : genres) {
                System.out.println(g.getId() + ". " + g.getName());
            }
            System.out.print("Select Genre ID: ");
            int genreId = scanner.nextInt();
            Genre genre = genres.stream()
                .filter(g -> g.getId() == genreId)
                .findFirst()
                .orElseThrow(() -> new ServiceException("Invalid genre ID"));
            
            // Display available languages
            List<Language> languages = languageService.getAllLanguages();
            System.out.println("\nAvailable Languages:");
            for (Language l : languages) {
                System.out.println(l.getId() + ". " + l.getName());
            }
            System.out.print("Select Language ID: ");
            int languageId = scanner.nextInt();
            Language language = languages.stream()
                .filter(l -> l.getId() == languageId)
                .findFirst()
                .orElseThrow(() -> new ServiceException("Invalid language ID"));
            
            System.out.print("Publication Year: ");
            int year = scanner.nextInt();
            
            System.out.print("Number of Copies: ");
            int copies = scanner.nextInt();
            
            System.out.print("Is Borrowable (true/false): ");
            boolean isBorrowable = scanner.nextBoolean();
            
            Book book = new Book(0, isbn, title, author, publisher, 
                               language, Year.of(year), copies, 
                               genre, isBorrowable);
            
            Book savedBook = bookService.addBook(book);
            System.out.println("Book added successfully with ID: " + savedBook.getId());
            
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleUpdateBook() {
        try {
            System.out.print("Enter Book ID to update: ");
            int bookId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            // First find the existing book
            List<Book> books = bookService.searchByTitle("");
            Book existingBook = books.stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new ServiceException("Book not found"));
            
            // Show current details and get updates
            System.out.println("\nCurrent Book Details:");
            displayBookDetails(existingBook);
            
            System.out.println("\nEnter new details (press Enter to keep current value):");
            
            System.out.print("Title [" + existingBook.getTitle() + "]: ");
            String title = scanner.nextLine();
            title = title.isEmpty() ? existingBook.getTitle() : title;
            
            System.out.print("Available Copies [" + existingBook.getAvailableCopies() + "]: ");
            String copiesStr = scanner.nextLine();
            int copies = copiesStr.isEmpty() ? 
                existingBook.getAvailableCopies() : Integer.parseInt(copiesStr);
            
            Book updatedBook = new Book(
                bookId,
                existingBook.getIsbn(),
                title,
                existingBook.getAuthor(),
                existingBook.getPublisher(),
                existingBook.getLanguage(),
                existingBook.getYear(),
                copies,
                existingBook.getGenre(),
                existingBook.isBorrowable()
            );
            
            if (bookService.updateBook(updatedBook)) {
                System.out.println("Book updated successfully");
            } else {
                System.out.println("Failed to update book");
            }
            
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void handleSearchBook() {
        try {
            System.out.println("\nSearch Options:");
            System.out.println("1. Search by Title");
            System.out.println("2. Search by Author");
            System.out.println("3. Search by Genre");
            System.out.println("4. Search by Language");
            System.out.println("5. View Available Books");
            
            System.out.print("Choose search option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            List<Book> results = null;
            
            switch (option) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    results = bookService.searchByTitle(title);
                    break;
                    
                case 2:
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    results = bookService.findByAuthor(author);
                    break;
                    
                case 3:
                    List<Genre> genres = genreService.getAllGenres();
                    System.out.println("\nAvailable Genres:");
                    genres.forEach(g -> System.out.println(g.getId() + ". " + g.getName()));
                    System.out.print("Select Genre ID: ");
                    int genreId = scanner.nextInt();
                    Genre genre = genres.stream()
                        .filter(g -> g.getId() == genreId)
                        .findFirst()
                        .orElseThrow(() -> new ServiceException("Invalid genre ID"));
                    results = bookService.findByGenre(genre);
                    break;
                    
                case 4:
                    List<Language> languages = languageService.getAllLanguages();
                    System.out.println("\nAvailable Languages:");
                    languages.forEach(l -> System.out.println(l.getId() + ". " + l.getName()));
                    System.out.print("Select Language ID: ");
                    int langId = scanner.nextInt();
                    Language language = languages.stream()
                        .filter(l -> l.getId() == langId)
                        .findFirst()
                        .orElseThrow(() -> new ServiceException("Invalid language ID"));
                    results = bookService.findByLanguage(language);
                    break;
                    
                case 5:
                    results = bookService.getAvailableBooks();
                    break;
                    
                default:
                    System.out.println("Invalid option");
                    return;
            }
            
            if (results != null && !results.isEmpty()) {
                System.out.println("\nFound " + results.size() + " books:");
                results.forEach(this::displayBookDetails);
            } else {
                System.out.println("No books found");
            }
            
        } catch (ServiceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayBookDetails(Book book) {
        System.out.println("\n----------------------------------------");
        System.out.println("ID: " + book.getId());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Publisher: " + book.getPublisher());
        System.out.println("Year: " + book.getYear());
        System.out.println("Available Copies: " + book.getAvailableCopies());
        System.out.println("Genre: " + book.getGenre().getName());
        System.out.println("Language: " + book.getLanguage().getName());
        System.out.println("Borrowable: " + book.isBorrowable());
        System.out.println("----------------------------------------");
    }
}
