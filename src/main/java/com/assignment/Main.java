package com.assignment;

import com.assignment.cli.LibraryManagementCLI;
import com.assignment.gui.LoginForm;
import com.assignment.gui.MainWindow;
import com.assignment.service.ServiceException;

public class Main {
    public static void main(String[] args) throws ServiceException {
        //MainWindow.main(args, null);
        LoginForm.main(args);
    }
}