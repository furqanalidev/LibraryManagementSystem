CREATE DATABASE library;

USE library;


CREATE TABLE `Language` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Genre` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `isbn` VARCHAR(50) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `author` VARCHAR(100) NOT NULL,
  `publisher` VARCHAR(100) NOT NULL,
  `languageId` INT NOT NULL,
  `year` YEAR NOT NULL,
  `availableCopies` INT NOT NULL,
  `genreId` INT NOT NULL,
  `isBorrowable` TINYINT(1) NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_isbn` (`isbn`),
  CONSTRAINT `FK_Book_Language` FOREIGN KEY (`languageId`) REFERENCES `Language` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `FK_Book_Genre` FOREIGN KEY (`genreId`) REFERENCES `Genre` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Magazine` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `availableCopies` INT NOT NULL,
  `isBorrowable` TINYINT(1) NOT NULL,
  `genreId` INT NOT NULL,
  `languageId` INT NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Genre_TO_Magazine` FOREIGN KEY (`genreId`) REFERENCES `Genre` (`id`),
  CONSTRAINT `FK_Language_TO_Magazine` FOREIGN KEY (`languageId`) REFERENCES `Language` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `Staff` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(50) NOT NULL,
  `lastName` VARCHAR(50) NOT NULL,
  `cnic` BIGINT NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `contact` VARCHAR(20) NULL,
  `email` VARCHAR(100) NULL,
  `occupation` ENUM('LIBRARIAN', 'MANAGER', 'ADMIN') NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_staff_cnic` (`cnic`),
  UNIQUE KEY `UQ_staff_contact` (`contact`),
  UNIQUE KEY `UQ_staff_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(50) NOT NULL,
  `lastName` VARCHAR(50) NULL,
  `cnic` BIGINT NOT NULL,
  `address` VARCHAR(255) NULL,
  `contact` VARCHAR(20) NULL,
  `email` VARCHAR(100) NULL,
  `borrowings` INT NOT NULL,
  `bookBorrowLimit` INT NOT NULL,
  `magazineBorrowLimit` INT NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_user_cnic` (`cnic`),
  UNIQUE KEY `UQ_user_contact` (`contact`),
  UNIQUE KEY `UQ_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `BookBorrow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bookId` INT NOT NULL,
  `date` DATETIME NOT NULL,
  `status` ENUM('PENDING', 'BORROWED', 'RETURNED', 'OVERDUE') NOT NULL,
  `staffId` INT,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Book_TO_BookBorrow` FOREIGN KEY (`bookId`) REFERENCES `Book` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_Staff_TO_BookBorrow` FOREIGN KEY (`staffId`) REFERENCES `Staff` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `MagazineBorrow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` DATETIME NOT NULL,
  `status` ENUM('PENDING', 'BORROWED', 'RETURNED', 'OVERDUE') NOT NULL,
  `magazineId` INT NOT NULL,
  `staffId` INT,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Magazine_TO_MagazineBorrow` FOREIGN KEY (`magazineId`) REFERENCES `Magazine` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_Staff_TO_MagazineBorrow` FOREIGN KEY (`staffId`) REFERENCES `Staff` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `UserBookBorrow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `bookBorrowId` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_User_TO_UserBookBorrow` FOREIGN KEY (`userId`) REFERENCES `User` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_BookBorrow_TO_UserBookBorrow` FOREIGN KEY (`bookBorrowId`) REFERENCES `BookBorrow` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `UserMagazineBorrow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `magazineBorrowId` INT NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_MagazineBorrow_TO_UserMagazineBorrow` FOREIGN KEY (`magazineBorrowId`) REFERENCES `MagazineBorrow` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_User_TO_UserMagazineBorrow` FOREIGN KEY (`userId`) REFERENCES `User` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `BookFine` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userBookBorrowId` INT NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `status` ENUM('PAID', 'UNPAID') NOT NULL,
  `reason` VARCHAR(255) NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_UserBookBorrow_TO_BookFine` FOREIGN KEY (`userBookBorrowId`) REFERENCES `UserBookBorrow` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `MagazineFine` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userMagazineBorrowId` INT NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `status` ENUM('PAID', 'UNPAID') NOT NULL,
  `reason` VARCHAR(255) NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_UserMagazineBorrow_TO_MagazineFine` FOREIGN KEY (`userMagazineBorrowId`) REFERENCES `UserMagazineBorrow` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `UserActivityLog` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `action` ENUM('BORROW', 'RETURN', 'FINE_PAID', 'FINE_ISSUED') NOT NULL,
  `referenceId` INT NOT NULL,
  `referenceType` ENUM('BOOK', 'MAGAZINE') NOT NULL,
  `date` DATETIME NOT NULL,
  `details` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_User_TO_UserActivityLog` FOREIGN KEY (`userId`) REFERENCES `User` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE INDEX `idx_magazine_id` ON `Magazine` (`id`);
CREATE INDEX `idx_book_isbn` ON `Book` (`isbn`);
CREATE INDEX `idx_user_email` ON `User` (`email`);
CREATE INDEX `idx_staff_email` ON `Staff` (`email`);

-- Add username column to Staff table
ALTER TABLE `Staff` 
ADD COLUMN `username` VARCHAR(50) NOT NULL AFTER `id`,
ADD UNIQUE KEY `UQ_staff_username` (`username`);

-- Add username column to User table
ALTER TABLE `User`
ADD COLUMN `username` VARCHAR(50) NOT NULL AFTER `id`,
ADD UNIQUE KEY `UQ_user_username` (`username`);

-- Create new credentials table
CREATE TABLE `Credentials` (
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
