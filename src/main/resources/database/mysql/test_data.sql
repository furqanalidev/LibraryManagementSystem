-- Insert into Language table
INSERT INTO `Language` (`name`) VALUES 
('English'), 
('Spanish'), 
('French'), 
('German'), 
('Mandarin');


-- Insert into Genre table
INSERT INTO `Genre` (`name`) VALUES 
('Fiction'), 
('Non-Fiction'), 
('Science Fiction'), 
('Mystery'), 
('Fantasy'), 
('Biography');


-- Insert into Book table
INSERT INTO `Book` (`isbn`, `title`, `author`, `publisher`, `languageId`, `year`, `availableCopies`, `genreId`, `isBorrowable`) VALUES 
('978-3-16-148410-0', 'The Great Gatsby', 'F. Scott Fitzgerald', 'Scribner', 1, 1925, 5, 1, 1),
('978-1-86197-876-9', 'Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'Harper', 1, 2011, 3, 2, 1),
('978-0-7432-7356-5', 'Dune', 'Frank Herbert', 'Chilton Books', 1, 1965, 2, 3, 1),
('978-0-452-28423-4', 'The Da Vinci Code', 'Dan Brown', 'Doubleday', 1, 2003, 4, 4, 1),
('978-0-06-231609-7', 'The Alchemist', 'Paulo Coelho', 'HarperOne', 1, 1988, 6, 5, 1);


-- Insert into Magazine table
INSERT INTO `Magazine` (`title`, `availableCopies`, `isBorrowable`, `genreId`, `languageId`) VALUES 
('National Geographic', 10, 1, 2, 1),
('Scientific American', 8, 1, 2, 1),
('Time Magazine', 12, 1, 2, 1),
('Der Spiegel', 5, 1, 2, 4),
('Le Monde Diplomatique', 6, 1, 2, 3);

-- Insert into Staff table
INSERT INTO `Staff` (`username`, `firstName`, `lastName`, `cnic`, `address`, `contact`, `email`, `occupation`) VALUES 
('librarian1', 'Alice', 'Smith', 1234567890123, '123 Library St.', '123-456-7890', 'alice.smith@example.com', 'LIBRARIAN'),
('assistant1', 'Bob', 'Johnson', 1234567890124, '456 Library Lane', '123-456-7891', 'bob.johnson@example.com', 'ASSISTANT'),
('admin1', 'Charlie', 'Brown', 1234567890125, '789 Library Ave.', '123-456-7892', 'charlie.brown@example.com', 'ADMIN');


-- Insert into User table
INSERT INTO `User` (`username`, `firstName`, `lastName`, `cnic`, `address`, `contact`, `email`, `borrowings`, `bookBorrowLimit`, `magazineBorrowLimit`) VALUES 
('user1', 'David', 'Miller', 1234567890126, '321 Reader Rd.', '123-456-7893', 'david.miller@example.com', 0, 5, 2),
('user2', 'Emma', 'Davis', 1234567890127, '654 Reader St.', '123-456-7894', 'emma.davis@example.com', 1, 3, 1),
('user3', 'Frank', 'Wilson', 1234567890128, '987 Reader Ave.', '123-456-7895', 'frank.wilson@example.com', 2, 4, 3);


-- Insert into Credentials table
INSERT INTO `Credentials` (`username`, `password`) VALUES 
('librarian1', 'securepassword1'),
('assistant1', 'securepassword2'),
('admin1', 'securepassword3'),
('user1', 'userpassword1'),
('user2', 'userpassword2'),
('user3', 'userpassword3');


-- Insert into BookBorrow table
INSERT INTO `BookBorrow` (`bookId`, `date`, `status`, `staffId`) VALUES 
(1, '2024-12-10 10:30:00', 'BORROWED', 1),
(2, '2024-12-11 14:00:00', 'PENDING', 2),
(3, '2024-12-12 09:15:00', 'RETURNED', 1),
(4, '2024-12-13 13:45:00', 'OVERDUE', 3);


-- Insert into MagazineBorrow table
INSERT INTO `MagazineBorrow` (`magazineId`, `date`, `status`, `staffId`) VALUES 
(1, '2024-12-10 11:00:00', 'BORROWED', 2),
(2, '2024-12-11 15:00:00', 'PENDING', 3),
(3, '2024-12-12 10:30:00', 'RETURNED', 1),
(4, '2024-12-13 12:00:00', 'OVERDUE', 2);


-- Insert into UserBookBorrow table
INSERT INTO `UserBookBorrow` (`userId`, `bookBorrowId`) VALUES 
(1, 1),
(2, 2),
(3, 3),
(1, 4);


-- Insert into UserMagazineBorrow table
INSERT INTO `UserMagazineBorrow` (`magazineBorrowId`, `userId`) VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 1);


-- Insert into BookFine table
INSERT INTO `BookFine` (`userBookBorrowId`, `amount`, `status`, `reason`) VALUES 
(1, 50.00, 'UNPAID', 'Late return'),
(2, 25.00, 'PAID', 'Damaged book'),
(3, 0.00, 'PAID', 'No fine'),
(4, 100.00, 'UNPAID', 'Lost book');


-- Insert into MagazineFine table
INSERT INTO `MagazineFine` (`userMagazineBorrowId`, `amount`, `status`, `reason`) VALUES 
(1, 20.00, 'PAID', 'Late return'),
(2, 0.00, 'PAID', 'No fine'),
(3, 15.00, 'UNPAID', 'Damaged magazine'),
(4, 30.00, 'UNPAID', 'Lost magazine');


-- Insert into UserActivityLog table
INSERT INTO `UserActivityLog` (`userId`, `action`, `referenceId`, `referenceType`, `date`, `details`) VALUES 
(1, 'BORROW', 1, 'BOOK', '2024-12-10 10:45:00', 'Borrowed "The Great Gatsby"'),
(2, 'BORROW', 2, 'MAGAZINE', '2024-12-11 15:15:00', 'Borrowed "National Geographic"'),
(3, 'RETURN', 3, 'BOOK', '2024-12-12 10:00:00', 'Returned "Dune"'),
(1, 'FINE_PAID', 1, 'BOOK', '2024-12-13 11:30:00', 'Paid fine for late return');

