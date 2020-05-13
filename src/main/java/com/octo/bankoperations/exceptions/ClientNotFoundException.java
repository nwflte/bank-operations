package com.octo.bankoperations.exceptions;

public class ClientNotFoundException extends RuntimeException {
    private final Long id;

    public ClientNotFoundException(Long id)
    {
        super("Client could not be found with id: " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
