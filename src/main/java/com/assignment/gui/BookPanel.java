/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.assignment.gui;


import java.time.Year;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.assignment.dao.BookBorrowDao;
import com.assignment.dao.BookDao;
import com.assignment.dao.GenreDao;
import com.assignment.dao.LanguageDao;
import com.assignment.dao.UserBookBorrowDao;
import com.assignment.dao.mysql.MySqlBookBorrowDao;
import com.assignment.dao.mysql.MySqlBookDao;
import com.assignment.dao.mysql.MySqlGenreDao;
import com.assignment.dao.mysql.MySqlLanguageDao;
import com.assignment.dao.mysql.MySqlUserBookBorrowDao;
import com.assignment.data.Book;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import com.assignment.service.BookService;
import com.assignment.service.BorrowService;
import com.assignment.service.DatabaseConnectionService;
import com.assignment.service.GenreService;
import com.assignment.service.LanguageService;
import com.assignment.service.impl.BookServiceImpl;
import com.assignment.service.impl.BorrowServiceImpl;
import com.assignment.service.impl.DatabaseConnectionServiceImpl;
import com.assignment.service.impl.GenreServiceImpl;
import com.assignment.service.impl.LanguageServiceImpl;
import com.formdev.flatlaf.ui.FlatRoundBorder;

/**
 *
 * @author meher
 */
public class BookPanel extends javax.swing.JPanel {
    private Book book;
    /**
     * Creates new form BookPanel
     * @param book Book to be displayed
     */
    public BookPanel(Book book, DrawMode drawMode) {
        initComponents();
        this.book = book;
        setAllText(book);
        switch (drawMode) {
            case LIBRARIAN:
            case MANAGER:
            case ADMIN:
                button.setText("Update");
                setAllEditable(true);
                setAllFocusable(true);
                loadComboBoxes();
                break;
        
            case USER:
                button.setText("Borrow");
                removeButton.setVisible(false);
            default:
                break;
        }
    }

        private void setAllEditable(boolean editable) {
        isbn.setEditable(editable);
        title.setEditable(editable);
        author.setEditable(editable);
        publisher.setEditable(editable);
        year.setEditable(editable);
        copies.setEditable(editable);
        borrowable.setEnabled(editable);
        //language.setEditable(editable);
        //genre.setEditable(editable);
        language.setEnabled(editable);
        genre.setEnabled(editable);
    }

    private void setAllFocusable(boolean focusable) {
        isbn.setFocusable(focusable);
        title.setFocusable(focusable);
        author.setFocusable(focusable);
        publisher.setFocusable(focusable);
        year.setFocusable(focusable);
        copies.setFocusable(focusable);
        borrowable.setFocusable(focusable);
        language.setFocusable(focusable);
        genre.setFocusable(focusable);
    }

    private boolean setAllText(Book book) {
        bookNo.setText("Book No. " + book.getId());
        isbn.setText(book.getIsbn());
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        year.setText(book.getYear().toString());
        copies.setText(Integer.toString(book.getAvailableCopies()));
        borrowable.setSelected(book.isBorrowable());
        language.removeAllItems();
        language.addItem(book.getLanguage().getName());
        genre.removeAllItems();
        genre.addItem(book.getGenre().getName());
        return true;
    }

    private boolean loadComboBoxes() {
        loadGenre();
        loadLanguage();
        return true;
    }

    private boolean loadGenre() {
        try {
            DatabaseConnectionService databaseConnectionService = new DatabaseConnectionServiceImpl();
            GenreDao genreDao = new MySqlGenreDao(databaseConnectionService.getConnection());
            GenreService genreService = new GenreServiceImpl(genreDao);
            List<Genre> genres = genreService.getAllGenres();
            for (Genre genrefromdb : genres) {
                genre.addItem(genrefromdb.getName());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean loadLanguage() {
        try {
            DatabaseConnectionService databaseConnectionService = new DatabaseConnectionServiceImpl();
            LanguageDao languageDao = new MySqlLanguageDao(databaseConnectionService.getConnection());
            LanguageService languageService = new LanguageServiceImpl(languageDao);
            List<Language> languages = languageService.getAllLanguages();
            for (Language languagefromdb : languages) {
                language.addItem(languagefromdb.getName());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        genre = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        isbn = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        title = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        author = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        publisher = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        year = new javax.swing.JTextField();
        copies = new javax.swing.JTextField();
        borrowable = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        language = new javax.swing.JComboBox<>();
        button = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        bookNo = new javax.swing.JLabel();

        setBorder(new FlatRoundBorder());

        jLabel2.setText("Title");

        genre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        genre.setEnabled(false);
        genre.setFocusable(false);

        jLabel3.setText("Author");

        jLabel4.setText("Publisher");

        jLabel5.setText("Language");

        isbn.setEditable(false);
        isbn.setToolTipText("");
        isbn.setBorder(new FlatRoundBorder());
        isbn.setFocusable(false);
        isbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isbnActionPerformed(evt);
            }
        });

        jLabel6.setText("Genre");

        title.setEditable(false);
        title.setBorder(new FlatRoundBorder());
        title.setFocusable(false);

        jLabel7.setText("Year");

        author.setEditable(false);
        author.setBorder(new FlatRoundBorder());
        author.setFocusable(false);

        jLabel8.setText("Copies");

        publisher.setEditable(false);
        publisher.setBorder(new FlatRoundBorder());
        publisher.setFocusable(false);

        jLabel9.setText("Borroable");

        year.setEditable(false);
        year.setBorder(new FlatRoundBorder());
        year.setFocusable(false);

        copies.setEditable(false);
        copies.setBorder(new FlatRoundBorder());
        copies.setFocusable(false);

        borrowable.setBorder(new FlatRoundBorder());
        borrowable.setEnabled(false);
        borrowable.setFocusable(false);
        borrowable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowableActionPerformed(evt);
            }
        });

        jLabel1.setText("ISBN");

        language.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        language.setEnabled(false);
        language.setFocusable(false);

        button.setBackground(new java.awt.Color(153, 153, 0));
        button.setText("Borrow");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        removeButton.setBackground(new java.awt.Color(204, 0, 51));
        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        bookNo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        bookNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bookNo.setText("Book No. ");
        bookNo.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(isbn)
                    .addComponent(title)
                    .addComponent(author)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(borrowable, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeButton)
                        .addGap(18, 18, 18)
                        .addComponent(button))
                    .addComponent(publisher)
                    .addComponent(language, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(genre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(year)
                    .addComponent(copies))
                .addGap(46, 46, 46))
            .addComponent(bookNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bookNo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(isbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(author, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(publisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(language, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(genre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(copies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button)
                        .addComponent(removeButton))
                    .addComponent(borrowable)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void isbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isbnActionPerformed
       
    }//GEN-LAST:event_isbnActionPerformed

    private void borrowableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowableActionPerformed
        
    }//GEN-LAST:event_borrowableActionPerformed

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        try {
            DatabaseConnectionService db = new DatabaseConnectionServiceImpl();
            BookDao bookDao = new MySqlBookDao(db.getConnection());
            switch (button.getText()) {
                case "Update":
                    BookService bookService = new BookServiceImpl(bookDao);
                    Book updatedBook = new Book(book.getId(), isbn.getText(), title.getText(), author.getText(),
                            publisher.getText(),
                            new Language(language.getSelectedIndex() + 1, language.getSelectedItem().toString()),
                            Year.of(Integer.parseInt(year.getText())), Integer.parseInt(copies.getText()),
                            new Genre(genre.getSelectedIndex() + 1, genre.getSelectedItem().toString()),
                            borrowable.isSelected());
                    if (book.equals(updatedBook)) {
                        JOptionPane.showMessageDialog(null, "No changes made");
                        return;
                    }
                    if (bookService.updateBook(updatedBook)) {
                        ((MainWindow)SwingUtilities.getWindowAncestor(this)).refreshBooks();
                        JOptionPane.showMessageDialog(null, "Book Updated");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found");
                    }

                case "Borrow":
                    BookBorrowDao bookBorrowDao = new MySqlBookBorrowDao(db.getConnection());
                    UserBookBorrowDao userBookBorrowDao = new MySqlUserBookBorrowDao(db.getConnection());
                    BorrowService bookBorrowService = new BorrowServiceImpl(bookBorrowDao, null, userBookBorrowDao, null, bookDao, null);
                    bookBorrowService.borrowBook(((MainWindow)SwingUtilities.getWindowAncestor(this)).getUser(), this.book, null);
                    ((MainWindow)SwingUtilities.getWindowAncestor(this)).refreshBooks();
                    JOptionPane.showMessageDialog(null, "Book Borrowed");
                    break;

                default:
                    System.exit(0);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_buttonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        try {
            DatabaseConnectionService db = new DatabaseConnectionServiceImpl();
            BookDao bookDao = new MySqlBookDao(db.getConnection());
            BookService bookService = new BookServiceImpl(bookDao);
            if (bookService.removeBook(book.getId())) {
                ((MainWindow)SwingUtilities.getWindowAncestor(this)).refreshBooks();
                JOptionPane.showMessageDialog(this, "Successfully Removed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error removing Book!");
            e.printStackTrace();
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField author;
    private javax.swing.JLabel bookNo;
    private javax.swing.JCheckBox borrowable;
    private javax.swing.JButton button;
    private javax.swing.JTextField copies;
    private javax.swing.JComboBox<String> genre;
    private javax.swing.JTextField isbn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<String> language;
    private javax.swing.JTextField publisher;
    private javax.swing.JButton removeButton;
    private javax.swing.JTextField title;
    private javax.swing.JTextField year;
    // End of variables declaration//GEN-END:variables
}
