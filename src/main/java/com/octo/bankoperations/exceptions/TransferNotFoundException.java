package com.octo.bankoperations.exceptions;

public class TransferNotFoundException extends RuntimeException {
    public TransferNotFoundException(String id) {
        super("Transfer could not be found with id: "+ id);
    }
}
