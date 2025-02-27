package org.securedata.helper.exception;

public class SecureJsonException extends RuntimeException {
public SecureJsonException(String message) {
super(message);
}
public SecureJsonException(String message, Throwable cause) {
super(message, cause);
}
}