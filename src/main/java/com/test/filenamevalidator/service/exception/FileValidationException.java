package com.test.filenamevalidator.service.exception;

public class FileValidationException extends Exception {
    public FileValidationException(String errorMessage) {
        super(errorMessage);
    }
}
