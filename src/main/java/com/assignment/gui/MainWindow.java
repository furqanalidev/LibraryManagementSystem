/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.assignment.gui;

import com.assignment.gui.panels.UserPanel;
import com.assignment.gui.panels.StaffPanel;
import com.assignment.gui.panels.MagazinePanel;
import com.assignment.gui.panels.BorrowPanel;
import com.assignment.gui.panels.BookPanel;
import com.assignment.gui.panels.BookFrom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import com.assignment.data.Person;
import com.assignment.data.Staff;
import com.assignment.data.User;
import com.assignment.service.ServiceException;
import com.assignment.service.ServiceFactory;
import com.formdev.flatlaf.ui.FlatRoundBorder;
import com.assignment.gui.theme.myTheme;

/**
 *
 * @author meher
 */
public class MainWindow extends javax.swing.JFrame {
    ServiceFactory serviceFactory = new ServiceFactory();
    DrawMode drawMode = DrawMode.USERDISPLAY;
    Person person;
    /**
     * Creates new form MainWindow
    * @throws ServiceException 
    */
    public MainWindow(Person person) throws ServiceException {
        initComponents();
        setLocationRelativeTo(null);
        this.person = person;
        if (person instanceof Staff) {
            loadUsers();
            Staff staff = (Staff) person;
            switch (staff.getOccupation()) {
                case ADMIN:
                    drawMode = DrawMode.ADMIN;
                    break;

                case LIBRARIAN:
                    drawMode = DrawMode.LIBRARIAN;
                    myTab.remove(userTab);
                    
                    break;

                case MANAGER:
                    drawMode = DrawMode.MANAGER;                    
                    break;

                default:
                    myTab.remove(staffTab);
                    break;
            }
        } else if (person instanceof User) {
            drawMode = DrawMode.USER;
            myTab.remove(userTab);
            myTab.remove(staffTab);
            addBookButton.setVisible(false);
            addMagazineButton.setVisible(false);
            addUserButton.setVisible(false);
            addStaffButton.setVisible(false);
            borrowingsOf.addItem(person.getUsername());
            borrowingsOf.setVisible(false);
            borrowingsOfLabel.setVisible(false);
        }
    }

    private void loadUsers() {
        borrowingsOf.removeAllItems();
        try {
            serviceFactory.getUserService().findAll().forEach(user -> {
                borrowingsOf.addItem(user.getUsername());
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error while loading users", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getUserIdByUsername(String username) {
        try {
            return serviceFactory.getUserService().findByUsername(username).getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void displayBooks() throws ServiceException {
        displayBookPanel.removeAll();
        try {
            serviceFactory.getBookService().getAvailableBooks().forEach(book -> {
            BookPanel bookPanel = new BookPanel(book, drawMode);
            displayBookPanel.add(bookPanel);
            displayBookPanel.revalidate();
        });
        } catch (ServiceException e) {
            throw new ServiceException("Error loading books");
        }
    }

    private void displayMagazines() throws ServiceException {
        displayMagazinePanel.removeAll();
        try {
            serviceFactory.getMagazineService().getAvailableMagazines().forEach(magazine -> {
            MagazinePanel magazinePanel = new MagazinePanel(magazine, drawMode);
            displayMagazinePanel.add(magazinePanel);
            displayMagazinePanel.revalidate();
        });
        } catch (ServiceException e) {
            throw new ServiceException("Error loading magazines");
        }
    }

    private void displayStaff() throws ServiceException {
        displayStaffPanel.removeAll();
        try{
            serviceFactory.getStaffService().findAll().forEach(staff -> {
                StaffPanel staffPanel = new StaffPanel(staff, DrawMode.UPDATE);
                displayStaffPanel.add(staffPanel);
                displayStaffPanel.revalidate();
            });
        }  catch (ServiceException e) {
            throw new ServiceException("Error loading staff");
        }
    }

    private void displayUser() throws ServiceException {
        displayUserPanel.removeAll();
        try{
            serviceFactory.getUserService().findAll().forEach(user -> {
                UserPanel userPanel = new UserPanel(user, drawMode);
                displayUserPanel.add(userPanel);
                displayUserPanel.revalidate();
            });
        }  catch (ServiceException e) {
            throw new ServiceException("Error loading users");
        }
    }

    private void displayBorrowedBooks() throws ServiceException {
        borrowedBooksPane.removeAll();
        try {
            serviceFactory.getBorrowService().getActiveBookBorrows(getUserIdByUsername(borrowingsOf.getSelectedItem().toString())).forEach(bookBorrow -> {
            BorrowPanel bookPanel = new BorrowPanel(bookBorrow, drawMode);
            borrowedBooksPane.add(bookPanel);
            borrowedBooksPane.revalidate();
        });
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new ServiceException("Error loading borrowed books");
        }
    }

    private void displayBorrowedMagazines() throws ServiceException {
        borrowedMagazinesTab.removeAll();
        try {
            serviceFactory.getBorrowService().getActiveMagazineBorrows(getUserIdByUsername(borrowingsOf.getSelectedItem().toString())).forEach(magazineBorrow -> {
            BorrowPanel magazinePanel = new BorrowPanel(magazineBorrow, drawMode);
            borrowedMagazinesTab.add(magazinePanel);
            borrowedMagazinesTab.revalidate();
        });
        } catch (ServiceException e) {
            throw new ServiceException("Error loading borrowed magazines");
        }
    }

    public void refreshBooks() {
        try {
            displayBookPanel.removeAll();
            displayBooks();
            displayBookPanel.revalidate();
            displayBookPanel.repaint();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error refreshing books");
        }
    }

    public void refresgBorrowings() {
        try {
            borrowedBooksPane.removeAll();
            borrowedMagazinesTab.removeAll();
            displayBorrowedBooks();
            displayBorrowedMagazines();
            borrowedTabbedPane.revalidate();
            borrowedTabbedPane.repaint();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(this, "Erroe refreshing borrowings", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshMagazines() {
        try {
            displayMagazinePanel.removeAll();
            displayMagazines();
            displayMagazinePanel.revalidate();
            displayMagazinePanel.repaint();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error refreshing magazines");
        }
    }

    public void refreshStaff() {
        try {
            displayStaffPanel.removeAll();
            displayStaff();
            displayStaffPanel.revalidate();
            displayStaffPanel.repaint();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error refreshing staff");
        }
    }
    public void refreshUsers() {
        try {
            displayUserPanel.removeAll();
            displayUser();
            displayUserPanel.revalidate();
            displayUserPanel.repaint();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "Error refreshing users");
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

        myTab = new javax.swing.JTabbedPane();
        bookTab = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        displayBookPanel = new javax.swing.JPanel();
        addBookButton = new javax.swing.JButton();
        magazineTab = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        displayMagazinePanel = new javax.swing.JPanel();
        addMagazineButton = new javax.swing.JButton();
        staffTab = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        displayStaffPanel = new javax.swing.JPanel();
        addStaffButton = new javax.swing.JButton();
        userTab = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        displayUserPanel = new javax.swing.JPanel();
        addUserButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        borrowedTabbedPane = new javax.swing.JTabbedPane();
        borrowedBooksPane = new javax.swing.JPanel();
        borrowedMagazinesTab = new javax.swing.JPanel();
        borrowingsOf = new javax.swing.JComboBox<>();
        borrowingsOfLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Library Management System");
        setPreferredSize(new java.awt.Dimension(1368, 720));
        setResizable(false);

        myTab.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        myTab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                myTabStateChanged(evt);
            }
        });
        myTab.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                myTabFocusGained(evt);
            }
        });

        bookTab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                bookTabComponentShown(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("All Available Books");

        displayBookPanel.setAutoscrolls(true);
        displayBookPanel.setMaximumSize(new java.awt.Dimension(1200, 32767));
        displayBookPanel.setName(""); // NOI18N

        addBookButton.setBackground(new java.awt.Color(0, 102, 51));
        addBookButton.setText("Add new Book");
        addBookButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bookTabLayout = new javax.swing.GroupLayout(bookTab);
        bookTab.setLayout(bookTabLayout);
        bookTabLayout.setHorizontalGroup(
            bookTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bookTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bookTabLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addBookButton, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(displayBookPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        bookTabLayout.setVerticalGroup(
            bookTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookTabLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(bookTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBookButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayBookPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addContainerGap())
        );

        myTab.addTab("Book", bookTab);

        magazineTab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                magazineTabComponentShown(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("All Available Magazines");

        displayMagazinePanel.setAutoscrolls(true);
        displayMagazinePanel.setMaximumSize(new java.awt.Dimension(1200, 32767));
        displayMagazinePanel.setName(""); // NOI18N

        addMagazineButton.setBackground(new java.awt.Color(0, 102, 51));
        addMagazineButton.setText("Add new Magazine");
        addMagazineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMagazineButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout magazineTabLayout = new javax.swing.GroupLayout(magazineTab);
        magazineTab.setLayout(magazineTabLayout);
        magazineTabLayout.setHorizontalGroup(
            magazineTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(magazineTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(magazineTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(displayMagazinePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, magazineTabLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addMagazineButton)))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        magazineTabLayout.setVerticalGroup(
            magazineTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(magazineTabLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(magazineTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addMagazineButton))
                .addGap(18, 18, 18)
                .addComponent(displayMagazinePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                .addContainerGap())
        );

        myTab.addTab("Magazine", magazineTab);

        staffTab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                staffTabComponentShown(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("All Staff");

        displayStaffPanel.setAutoscrolls(true);
        displayStaffPanel.setMaximumSize(new java.awt.Dimension(1200, 32767));
        displayStaffPanel.setName(""); // NOI18N
        displayStaffPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                displayStaffPanelComponentShown(evt);
            }
        });

        addStaffButton.setBackground(new java.awt.Color(0, 102, 51));
        addStaffButton.setText("Add new Staff");
        addStaffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStaffButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout staffTabLayout = new javax.swing.GroupLayout(staffTab);
        staffTab.setLayout(staffTabLayout);
        staffTabLayout.setHorizontalGroup(
            staffTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(staffTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(staffTabLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addStaffButton))
                    .addComponent(displayStaffPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 90, Short.MAX_VALUE))
        );
        staffTabLayout.setVerticalGroup(
            staffTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffTabLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(staffTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(addStaffButton))
                .addGap(18, 18, 18)
                .addComponent(displayStaffPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                .addContainerGap())
        );

        myTab.addTab("Staff", staffTab);

        userTab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                userTabComponentShown(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 36)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("All User");

        displayUserPanel.setAutoscrolls(true);
        displayUserPanel.setMaximumSize(new java.awt.Dimension(1200, 32767));
        displayUserPanel.setName(""); // NOI18N
        displayUserPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                displayUserPanelComponentShown(evt);
            }
        });

        addUserButton.setBackground(new java.awt.Color(0, 102, 51));
        addUserButton.setText("Add new User");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userTabLayout = new javax.swing.GroupLayout(userTab);
        userTab.setLayout(userTabLayout);
        userTabLayout.setHorizontalGroup(
            userTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(userTabLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addUserButton))
                    .addComponent(displayUserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 90, Short.MAX_VALUE))
        );
        userTabLayout.setVerticalGroup(
            userTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userTabLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(userTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addUserButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayUserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                .addContainerGap())
        );

        myTab.addTab("User", userTab);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("User Borrowings");
        jLabel5.setFocusable(false);

        borrowedBooksPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                borrowedBooksPaneComponentShown(evt);
            }
        });
        borrowedTabbedPane.addTab("Books", borrowedBooksPane);

        borrowedMagazinesTab.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                borrowedMagazinesTabComponentShown(evt);
            }
        });
        borrowedTabbedPane.addTab("Magazines", borrowedMagazinesTab);

        borrowingsOf.setBorder(new FlatRoundBorder());
        borrowingsOf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowingsOfActionPerformed(evt);
            }
        });

        borrowingsOfLabel.setText("Select User");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrowingsOfLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(borrowingsOf, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(borrowedTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borrowingsOf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borrowingsOfLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borrowedTabbedPane)
                .addContainerGap())
        );

        myTab.addTab("Borrowings", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myTab)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(myTab, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void myTabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_myTabStateChanged

    }//GEN-LAST:event_myTabStateChanged

    private void myTabFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_myTabFocusGained
        
    }//GEN-LAST:event_myTabFocusGained

    private void magazineTabComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_magazineTabComponentShown
        try {
            displayMagazines();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading magazines");
        }
    }//GEN-LAST:event_magazineTabComponentShown

    private void bookTabComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_bookTabComponentShown
        try {
            displayBooks();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading books");
        }
    }//GEN-LAST:event_bookTabComponentShown

    private void displayStaffPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_displayStaffPanelComponentShown
        // nothing to do here
    }//GEN-LAST:event_displayStaffPanelComponentShown

    private void staffTabComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_staffTabComponentShown
        try {
            displayStaff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_staffTabComponentShown

    private void displayUserPanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_displayUserPanelComponentShown
        //nothing to do here
    }//GEN-LAST:event_displayUserPanelComponentShown

    private void userTabComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_userTabComponentShown
        try {
            displayUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_userTabComponentShown

    private void addBookButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookButtonActionPerformed
        BookFrom.main(null, DrawMode.CREATE, null);
    }//GEN-LAST:event_addBookButtonActionPerformed

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        JDialog dialog = new JDialog(this, "Add new User", true);
        dialog.setSize(310, 550);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(new UserPanel(null, DrawMode.CREATE));
        dialog.setVisible(true);
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void addStaffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStaffButtonActionPerformed
        JDialog dialog = new JDialog(this, "Add new Staff", true);
        dialog.setSize(310, 470);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.add(new StaffPanel(null, DrawMode.CREATE));
        dialog.setVisible(true);
    }//GEN-LAST:event_addStaffButtonActionPerformed

    private void addMagazineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMagazineButtonActionPerformed
        JDialog dialog = new JDialog();
        dialog.setSize(400, 280);
        dialog.setResizable(false);
        dialog.setTitle("Add Magazine");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //dialog.setLayout(new BorderLayout());
        dialog.add(new MagazinePanel(null, DrawMode.CREATE));
        dialog.setVisible(true);
    }//GEN-LAST:event_addMagazineButtonActionPerformed

    private void borrowedBooksPaneComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_borrowedBooksPaneComponentShown
        try {
            displayBorrowedBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_borrowedBooksPaneComponentShown

    private void borrowedMagazinesTabComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_borrowedMagazinesTabComponentShown
        try {
            displayBorrowedMagazines();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_borrowedMagazinesTabComponentShown

    private void borrowingsOfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowingsOfActionPerformed
        refresgBorrowings();
    }//GEN-LAST:event_borrowingsOfActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[], Person person) throws ServiceException {
        myTheme.setup();
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainWindow(person).setVisible(true);
                } catch (ServiceException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public User getUser() {
        return (User) this.person;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBookButton;
    private javax.swing.JButton addMagazineButton;
    private javax.swing.JButton addStaffButton;
    private javax.swing.JButton addUserButton;
    private javax.swing.JPanel bookTab;
    private javax.swing.JPanel borrowedBooksPane;
    private javax.swing.JPanel borrowedMagazinesTab;
    private javax.swing.JTabbedPane borrowedTabbedPane;
    private javax.swing.JComboBox<String> borrowingsOf;
    private javax.swing.JLabel borrowingsOfLabel;
    private javax.swing.JPanel displayBookPanel;
    private javax.swing.JPanel displayMagazinePanel;
    private javax.swing.JPanel displayStaffPanel;
    private javax.swing.JPanel displayUserPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel magazineTab;
    private javax.swing.JTabbedPane myTab;
    private javax.swing.JPanel staffTab;
    private javax.swing.JPanel userTab;
    // End of variables declaration//GEN-END:variables
}
