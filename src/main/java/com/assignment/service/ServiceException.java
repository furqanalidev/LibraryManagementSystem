package com.assignment.service;

import javax.swing.JOptionPane;

/**
 * Custom exception class for service layer errors.
 *
 * @author MeHeR ALi
 */
public class ServiceException extends Exception {
    
    public ServiceException(String message) {
        super(message);
        JOptionPane.showMessageDialog(null, message);
    }
    
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
