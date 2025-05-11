# 📚 Library Management System

A comprehensive Library Management System built using **Java (Swing)** and **MySQL**, developed as part of coursework for **Object-Oriented Programming** and **Database Systems**. The system supports book and magazine borrowing, user and staff management, and automated fine calculation, providing a clean and functional GUI.

## 🚀 Features

- 🔐 **Authentication:** Secure login for both staff and users.
- 📘 **Book & Magazine Management:** Functionality to add, update, and delete book and magazine records.
- 👥 **User & Staff Management:** Registration and comprehensive record management for users and staff members.
- 📚 **Borrowing & Return System:** Efficient system for borrowing and returning books and magazines.
- ⏰ **Automated Fine Calculation:** Automatic calculation of fines for overdue returns.
- 🔍 **Search Functionality:** Robust search capabilities across all records within the system.
- 📈 **Statistics & Logging:** Tracking of borrowing statistics and detailed activity logs.
- 🛠️ **Real-time Updates (Triggers):** Implementation of database triggers for immediate updates, such as borrow counts.
- ✅ **Username Auto-fill:** Automatic population of usernames via database triggers during record creation.

## 🛠️ Tech Stack

- **Java:** Core application logic and graphical user interface developed using Swing.
- **MySQL:** Relational database management system used for the backend.
- **JDBC:** Java Database Connectivity API for seamless interaction between Java and MySQL.
- **SQL Triggers:** Automated database operations triggered by specific events.

## 🗃️ Database Schema Highlights

The database comprises over 10 well-structured tables, including:

- `Book`
- `Magazine`
- `User`
- `Staff`
- `BookBorrow`
- `MagazineBorrow`
- `UserBookBorrow`
- `UserMagazineBorrow`
- `BookFine`
- `MagazineFine`
- `UserActivityLog`
- `Credentials`

Additionally, the schema includes triggers designed to automatically update borrow counts and synchronize usernames across relevant tables.


## 📦 Requirements

- **Java Development Kit (JDK):** Version 8 or higher is required.
- **MySQL Server:** Ensure a MySQL server is installed and running.
- **JDBC Driver (Connector/J):** Necessary for Java to communicate with the MySQL database.
- **Integrated Development Environment (IDE):** IntelliJ IDEA or Eclipse is recommended for development.

## 🔧 Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/LibraryManagementSystem.git
   cd LibraryManagementSystem
   ```
   
2. **Set up the Database:**

- Import the ```src/main/resources/database/mysql/schema.sql``` file into your MySQL server to create the database schema.
- Create a dedicated MySQL user with the necessary privileges for the application or update the database connection credentials within the application code.


3. **Run the Project:**

- Open the project in your preferred IDE (IntelliJ IDEA or Eclipse).
- Ensure that the JDBC driver for MySQL is included in the project dependencies.
- Locate the main entry point of the application (Main.java) and run it.


## 🤝 Contributing
Contributions and pull requests are welcome! For significant changes, please open an issue first to discuss the proposed modifications.

## 🙋‍♂️ Author
Meher Ali
📧 [meherali.meer@gmail.com]
