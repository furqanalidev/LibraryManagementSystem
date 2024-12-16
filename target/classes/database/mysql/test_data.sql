-- Insert into Language table
INSERT INTO `Language` (`name`) VALUES 
('English'),
('French'),
('German'),
('Spanish'),
('Arabic');

-- Insert into Genre table
INSERT INTO `Genre` (`name`) VALUES 
('Fiction'),
('Non-Fiction'),
('Science'),
('History'),
('Fantasy');

-- Insert into Book table
INSERT INTO `Book` (`isbn`, `title`, `author`, `publisher`, `language_id`, `year`, `availableCopies`, `genre_id`, `isBorrowable`) VALUES
('978-3-16-148410-0', 'The Great Gatsby', 'F. Scott Fitzgerald', 'Scribner', 1, 1925, 5, 1, 1),
('978-0-7432-7356-5', 'A Brief History of Time', 'Stephen Hawking', 'Bantam Books', 1, 1988, 3, 3, 1),
('978-1-4028-9462-6', 'El Aleph', 'Jorge Luis Borges', 'Sur', 4, 1949, 2, 1, 0),
('978-0-14-044926-6', 'The Odyssey', 'Homer', 'Penguin Classics', 2, 1999, 4, 5, 1);

-- Insert into Magazine table
INSERT INTO `Magazine` (`title`, `availableCopies`, `isBorrowable`, `genreId`, `languageId`) VALUES
('National Geographic', 10, 1, 3, 1),
('Scientific American', 7, 1, 3, 1),
('Le Monde', 5, 1, 2, 2);

-- Insert into Staff table
INSERT INTO `Staff` (`firstName`, `lastName`, `cnic`, `address`, `contact`, `email`, `occupation`) VALUES
('John', 'Doe', 1234567890123, '123 Library St.', '555-1234', 'john.doe@example.com', 'LIBRARIAN'),
('Jane', 'Smith', 2345678901234, '456 Book Ave.', '555-5678', 'jane.smith@example.com', 'ASSISTANT');

-- Insert into User table
INSERT INTO `User` (`firstName`, `lastName`, `cnic`, `address`, `contact`, `email`, `borrowings`, `bookBorrowLimit`, `magazineBorrowLimit`) VALUES
('Alice', 'Brown', 9876543210987, '789 Reader Ln.', '555-8765', 'alice.brown@example.com', 0, 3, 2),
('Bob', 'Johnson', 8765432109876, '321 Borrower Rd.', '555-4321', 'bob.johnson@example.com', 1, 5, 3);

-- Insert into BookBorrow table
INSERT INTO `BookBorrow` (`bookId`, `date`, `status`, `staffId`) VALUES
(1, '2024-12-01 10:00:00', 'BORROWED', 1),
(2, '2024-12-02 11:00:00', 'RETURNED', 2);

-- Insert into MagazineBorrow table
INSERT INTO `MagazineBorrow` (`magazineId`, `date`, `status`, `staffId`) VALUES
(1, '2024-12-03 12:00:00', 'BORROWED', 1),
(2, '2024-12-04 13:00:00', 'PENDING', 2);

-- Insert into UserBookBorrow table
INSERT INTO `UserBookBorrow` (`userId`, `bookBorrowId`) VALUES
(1, 1),
(2, 2);

-- Insert into UserMagazineBorrow table
INSERT INTO `UserMagazineBorrow` (`userId`, `magazineBorrowId`) VALUES
(1, 1),
(2, 2);

-- Insert into BookFine table
INSERT INTO `BookFine` (`userBookBorrowId`, `amount`, `status`, `reason`) VALUES
(1, 50.00, 'UNPAID', 'Late return'),
(2, 25.00, 'PAID', 'Damaged book');

-- Insert into MagazineFine table
INSERT INTO `MagazineFine` (`userMagazineBorrowId`, `amount`, `status`, `reason`) VALUES
(1, 10.00, 'UNPAID', 'Overdue'),
(2, 5.00, 'PAID', 'Lost magazine');

-- Insert into UserActivityLog table
INSERT INTO `UserActivityLog` (`userId`, `action`, `referenceId`, `referenceType`, `date`, `details`) VALUES
(1, 'BORROW', 1, 'BOOK', '2024-12-01 10:15:00', 'Borrowed The Great Gatsby'),
(2, 'RETURN', 2, 'BOOK', '2024-12-02 11:30:00', 'Returned A Brief History of Time');
