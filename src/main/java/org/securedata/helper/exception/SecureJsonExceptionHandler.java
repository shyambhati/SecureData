package org.securedata.helper.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SecureJsonExceptionHandler {
private static final Logger logger = Logger.getLogger(SecureJsonExceptionHandler.class.getName());

public static void handleException(Exception e) {
    // Log the exception details
    logger.log(Level.SEVERE, "An error occurred in the SecureJson : {0}", e.getMessage());

    // Optionally: Throw a LibraryException or handle according to your requirements
    throw new SecureJsonException("A SecureJson-specific error occurred", e);
}
}