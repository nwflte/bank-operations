package com.octo.bankoperations.exceptions;

public class CompteNonExistantException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private Long id;

  public CompteNonExistantException(){
    super("Compte could not be found");
  }

  public CompteNonExistantException(Long id) {
    super("Compte could not be found with id: " + id);
    this.id = id;
  }

  public CompteNonExistantException(String rib) {
    super("Compte could not be found with rib: " + rib);
  }

  public Long getId() {
    return id;
  }
}
