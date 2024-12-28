/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.assignment.gui.panels;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.assignment.dao.GenreDao;
import com.assignment.dao.LanguageDao;
import com.assignment.dao.MagazineBorrowDao;
import com.assignment.dao.MagazineDao;
import com.assignment.dao.UserMagazineBorrowDao;
import com.assignment.dao.mysql.MySqlGenreDao;
import com.assignment.dao.mysql.MySqlLanguageDao;
import com.assignment.dao.mysql.MySqlMagazineBorrowDao;
import com.assignment.dao.mysql.MySqlMagazineDao;
import com.assignment.dao.mysql.MySqlUserMagazineBorrowDao;
import com.assignment.data.Genre;
import com.assignment.data.Language;
import com.assignment.data.Magazine;
import com.assignment.gui.DrawMode;
import com.assignment.gui.MainWindow;
import com.assignment.service.BorrowService;
import com.assignment.service.DatabaseConnectionService;
import com.assignment.service.GenreService;
import com.assignment.service.LanguageService;
import com.assignment.service.MagazineService;
import com.assignment.service.impl.BorrowServiceImpl;
import com.assignment.service.impl.DatabaseConnectionServiceImpl;
import com.assignment.service.impl.GenreServiceImpl;
import com.assignment.service.impl.LanguageServiceImpl;
import com.assignment.service.impl.MagazineServiceImpl;
import com.formdev.flatlaf.ui.FlatRoundBorder;

/**
 *
 * @author meher
 */
public class MagazinePanel extends javax.swing.JPanel {
    private Magazine magazine;

    /**
     * Creates new form MagazinePanel
     */
    public MagazinePanel(Magazine magazine, DrawMode drawMode) {
        initComponents();
        this.magazine = magazine;
        switch (drawMode) {
            case LIBRARIAN:
            case MANAGER:
            case ADMIN:
                button.setText("Update");
                setAllText(magazine);
                setAllEditable(true);
                setAllFocusable(true);
                button.setVisible(true);
                loadComboBoxes();
                break;

            case CREATE:
                button.setText("Add");
                setAllEditable(true);
                setAllFocusable(true);
                removeButton.setVisible(false);
                genre.removeAllItems();
                language.removeAllItems();
                loadComboBoxes();
                break;
        
            case USER:
                button.setText("Borrow");
                setAllText(magazine);
                removeButton.setVisible(false);
            default:
                break;
        }
    }

    private void setAllText(Magazine magazine) {
        title.setText(magazine.getTitle());
        language.removeAllItems();
        language.addItem(magazine.getLanguage().getName());
        genre.removeAllItems();
        genre.addItem(magazine.getGenre().getName());
        copies.setText(Integer.toString(magazine.getAvailableCopies()));
        borrowable.setSelected(magazine.isBorrowable());
        button.setVisible(magazine.isBorrowable());
    }
    private void setAllEditable(boolean editable) {
        title.setEditable(editable);
        language.setEnabled(editable);
        genre.setEnabled(editable);
        copies.setEditable(editable);
        borrowable.setEnabled(editable);
    }
    private void setAllFocusable(boolean focusable) {
        title.setFocusable(focusable);
        language.setFocusable(focusable);
        genre.setFocusable(focusable);
        copies.setFocusable(focusable);
        borrowable.setFocusable(focusable);
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

        jLabel5 = new javax.swing.JLabel();
        genre = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        title = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        button = new javax.swing.JButton();
        language = new javax.swing.JComboBox<>();
        borrowable = new javax.swing.JCheckBox();
        copies = new javax.swing.JTextField();
        removeButton = new javax.swing.JButton();

        setBorder(new FlatRoundBorder());

        jLabel5.setText("Language");

        genre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        genre.setEnabled(false);
        genre.setFocusable(false);

        jLabel9.setText("Borroable");

        jLabel2.setText("Title");

        jLabel8.setText("Copies");

        title.setEditable(false);
        title.setBorder(new FlatRoundBorder());
        title.setFocusable(false);

        jLabel6.setText("Genre");

        button.setBackground(new java.awt.Color(0, 102, 204));
        button.setText("Borrow");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        language.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        language.setEnabled(false);
        language.setFocusable(false);

        borrowable.setBorder(new FlatRoundBorder());
        borrowable.setEnabled(false);
        borrowable.setFocusable(false);
        borrowable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowableActionPerformed(evt);
            }
        });

        copies.setEditable(false);
        copies.setBorder(new FlatRoundBorder());
        copies.setFocusable(false);

        removeButton.setBackground(new java.awt.Color(204, 0, 51));
        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(title)
                    .addComponent(language, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(genre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(copies)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(borrowable, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeButton)
                        .addGap(18, 18, 18)
                        .addComponent(button)))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(language, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(copies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borrowable)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button)
                        .addComponent(removeButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void borrowableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowableActionPerformed
        
    }//GEN-LAST:event_borrowableActionPerformed

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        try {
            DatabaseConnectionService db = new DatabaseConnectionServiceImpl();
            MagazineDao magazineDao = new MySqlMagazineDao(db.getConnection());
            MagazineService magazineService = new MagazineServiceImpl(magazineDao);
            switch (button.getText()) {
                case "Add":
                    Magazine newMagazine = new Magazine(0,title.getText(), Integer.parseInt(copies.getText()), borrowable.isSelected(),
                                                        new Genre(genre.getSelectedIndex() + 1, genre.getSelectedItem().toString()),
                                                        new Language(language.getSelectedIndex() + 1, language.getSelectedItem().toString()));
                    magazineService.addMagazine(newMagazine);
                    ((MainWindow) SwingUtilities.getWindowAncestor((JDialog) SwingUtilities.getWindowAncestor(this))).refreshStaff();
                    JOptionPane.showMessageDialog(null, "Magazine Added");
                    break;

                case "Update":
                    //MagazineService magazineService = new MagazineServiceImpl(magazineDao);
                    Magazine updatedMagazine = new Magazine(magazine.getId(), title.getText(), Integer.parseInt(copies.getText()), borrowable.isSelected(),
                                                            new Genre(genre.getSelectedIndex() + 1, genre.getSelectedItem().toString()),
                                                            new Language(language.getSelectedIndex() + 1, language.getSelectedItem().toString()));
                    if (magazine.equals(updatedMagazine)) {
                        JOptionPane.showMessageDialog(null, "No changes made");
                        return;
                    }
                    if (magazineService.updateMagazine(updatedMagazine)) {
                        ((MainWindow)SwingUtilities.getWindowAncestor(this)).refreshMagazines();
                        JOptionPane.showMessageDialog(null, "Magazine Updated");
                    } else {
                        JOptionPane.showMessageDialog(null, "Magazine not found");
                    }
                    break;

                case "Borrow":
                    MagazineBorrowDao magazineBorrowDao = new MySqlMagazineBorrowDao(db.getConnection());
                    UserMagazineBorrowDao userMagazineBorrowDao = new MySqlUserMagazineBorrowDao(db.getConnection());
                    BorrowService magazineBorrowService = new BorrowServiceImpl(magazineBorrowDao, userMagazineBorrowDao, magazineDao);
                    magazineBorrowService.borrowMagazine(((MainWindow)SwingUtilities.getWindowAncestor(this)).getUser(), this.magazine, null);
                    ((MainWindow)SwingUtilities.getWindowAncestor(this)).refreshMagazines();
                    JOptionPane.showMessageDialog(null, "Magazine Borrowed");
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
            MagazineDao magazineDao = new MySqlMagazineDao(db.getConnection());
            MagazineService magazineService = new MagazineServiceImpl(magazineDao);
            if (magazineService.removeMagazine(magazine.getId())) {
                ((MainWindow)SwingUtilities.getWindowAncestor(this)).refreshMagazines();
                JOptionPane.showMessageDialog(this, "Successfully Removed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error removing Magazine!");
            e.printStackTrace();
        }
    }//GEN-LAST:event_removeButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox borrowable;
    private javax.swing.JButton button;
    private javax.swing.JTextField copies;
    private javax.swing.JComboBox<String> genre;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<String> language;
    private javax.swing.JButton removeButton;
    private javax.swing.JTextField title;
    // End of variables declaration//GEN-END:variables
}
