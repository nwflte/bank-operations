package com.octo.bankoperations.exceptions;

public class UtilisateurNotFoundException extends RuntimeException {
    private final Long id;

    public UtilisateurNotFoundException(Long id)
    {
        super("Utilisateur could not be found with id: " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
