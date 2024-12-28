/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.assignment.gui;

import java.time.Year;
import java.util.List;

import javax.swing.JOptionPane;

import com.assignment.dao.BookDao;
import com.assignment.dao.GenreDao;
import com.assignment.dao.LanguageDao;
import com.assignment.dao.mysql.MySqlBookDao;
import com.assignment.dao.mysql.MySqlGenreDao;
import com.assignment.dao.mysql.MySqlLanguageDao;
import com.assignment.data.Book;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import com.assignment.service.BookService;
import com.assignment.service.DatabaseConnectionService;
import com.assignment.service.GenreService;
import com.assignment.service.LanguageService;
import com.assignment.service.impl.BookServiceImpl;
import com.assignment.service.impl.DatabaseConnectionServiceImpl;
import com.assignment.service.impl.GenreServiceImpl;
import com.assignment.service.impl.LanguageServiceImpl;
import com.assignment.theme.myTheme;
import com.formdev.flatlaf.ui.FlatRoundBorder;

/**
 *
 * @author meher
 */
public class BookFrom extends javax.swing.JDialog {
    private Book book;

    /**
     * Creates new form Book
     * 
     */
    public BookFrom(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public BookFrom(java.awt.Frame parent, boolean modal, DrawMode mode, Book book) {
        super(parent, modal);
        this.book = book;
        initComponents();
        switch (mode) {
            case UPDATE:
                setTitle("Edit Book");
                setAllEditable(true);
                setAllFocusable(true);
                jButton1.setText("Update");
                setAllText(book);
                loadComboBoxes();
                break;
            case CREATE:
                setTitle("Add Book");
                setAllEditable(true);
                setAllFocusable(true);
                genre.removeAllItems();
                genre.addItem("Select Genre");
                language.removeAllItems();
                language.addItem("Select Language");
                jButton1.setText("Add");
                loadComboBoxes();
                break;
            default:
                setTitle("Book Details");
                setAllEditable(true);
                setAllText(book);
                setAllEditable(false);
                setAllFocusable(false);
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
        language.setEditable(editable);
        genre.setEditable(editable);
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
    // @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        isbn = new javax.swing.JTextField();
        title = new javax.swing.JTextField();
        author = new javax.swing.JTextField();
        publisher = new javax.swing.JTextField();
        year = new javax.swing.JTextField();
        copies = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        borrowable = new javax.swing.JCheckBox();
        language = new javax.swing.JComboBox<>();
        genre = new javax.swing.JComboBox<>();

        setAlwaysOnTop(true);
        setLocationByPlatform(true);
        setResizable(false);

        isbn.setEditable(false);
        isbn.setToolTipText("");
        isbn.setBorder(new FlatRoundBorder());
        isbn.setFocusable(false);
        isbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isbnActionPerformed(evt);
            }
        });

        title.setEditable(false);
        title.setBorder(new FlatRoundBorder());
        title.setFocusable(false);

        author.setEditable(false);
        author.setBorder(new FlatRoundBorder());
        author.setFocusable(false);

        publisher.setEditable(false);
        publisher.setBorder(new FlatRoundBorder());
        publisher.setFocusable(false);

        year.setEditable(false);
        year.setBorder(new FlatRoundBorder());
        year.setFocusable(false);

        copies.setEditable(false);
        copies.setBorder(new FlatRoundBorder());
        copies.setFocusable(false);

        jLabel1.setText("ISBN");

        jLabel2.setText("Title");

        jLabel3.setText("Author");

        jLabel4.setText("Publisher");

        jLabel5.setText("Language");

        jLabel6.setText("Genre");

        jLabel7.setText("Year");

        jLabel8.setText("Copies");

        jLabel9.setText("Borroable");

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setText("Add");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        borrowable.setBorder(new FlatRoundBorder());
        borrowable.setEnabled(false);
        borrowable.setFocusable(false);
        borrowable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowableActionPerformed(evt);
            }
        });

        language.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        language.setEnabled(false);
        language.setFocusable(false);

        genre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        genre.setEnabled(false);
        genre.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
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
                        .addGap(0, 108, Short.MAX_VALUE))
                    .addComponent(publisher)
                    .addComponent(language, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(genre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(year)
                    .addComponent(copies))
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
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
                    .addComponent(jLabel9)
                    .addComponent(borrowable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void borrowableActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_borrowableActionPerformed
        // nothing to do here
    }// GEN-LAST:event_borrowableActionPerformed

    private void isbnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_isbnActionPerformed
        // nothing to do here
    }// GEN-LAST:event_isbnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        try {
            DatabaseConnectionService db = new DatabaseConnectionServiceImpl();
            BookDao bookDao = new MySqlBookDao(db.getConnection());
            BookService bookService = new BookServiceImpl(bookDao);
            switch (jButton1.getText()) {
                case "Add":
                    Book newBook = new Book(0, isbn.getText(), title.getText(), author.getText(), publisher.getText(),
                            new Language(language.getSelectedIndex() + 1, language.getSelectedItem().toString()),
                            Year.of(Integer.parseInt(year.getText())), Integer.parseInt(copies.getText()),
                            new Genre(genre.getSelectedIndex() + 1, genre.getSelectedItem().toString()),
                            borrowable.isSelected());
                    bookService.addBook(newBook);
                    JOptionPane.showMessageDialog(null, "Book Added");
                    break;

                case "Update":
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
                        JOptionPane.showMessageDialog(null, "Book Updated");
                    } else {
                        JOptionPane.showMessageDialog(null, "Book not found");
                    }
                default:
                    System.exit(0);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[], DrawMode mode, Book book) {
        myTheme.setup();
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        // try {
        // for (javax.swing.UIManager.LookAndFeelInfo info :
        // javax.swing.UIManager.getInstalledLookAndFeels()) {
        // if ("Nimbus".equals(info.getName())) {
        // javax.swing.UIManager.setLookAndFeel(info.getClassName());
        // break;
        // }
        // }
        // } catch (ClassNotFoundException ex) {
        // java.util.logging.Logger.getLogger(BookFrom.class.getName()).log(java.util.logging.Level.SEVERE,
        // null, ex);
        // } catch (InstantiationException ex) {
        // java.util.logging.Logger.getLogger(BookFrom.class.getName()).log(java.util.logging.Level.SEVERE,
        // null, ex);
        // } catch (IllegalAccessException ex) {
        // java.util.logging.Logger.getLogger(BookFrom.class.getName()).log(java.util.logging.Level.SEVERE,
        // null, ex);
        // } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        // java.util.logging.Logger.getLogger(BookFrom.class.getName()).log(java.util.logging.Level.SEVERE,
        // null, ex);
        // }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                BookFrom dialog = new BookFrom(new javax.swing.JFrame(), true, mode, book);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        dialog.dispose();
                    }
                });
                
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField author;
    private javax.swing.JCheckBox borrowable;
    private javax.swing.JTextField copies;
    private javax.swing.JComboBox<String> genre;
    private javax.swing.JTextField isbn;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JTextField title;
    private javax.swing.JTextField year;
    // End of variables declaration//GEN-END:variables
}
