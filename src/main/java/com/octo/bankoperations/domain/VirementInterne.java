package com.octo.bankoperations.domain;

import javax.persistence.*;

@Entity
public class VirementInterne extends Virement {

  @ManyToOne
  private Compte compteBeneficiaire;

  public Compte getCompteBeneficiaire() {
    return compteBeneficiaire;
  }

  public void setCompteBeneficiaire(Compte compteBeneficiaire) {
    this.compteBeneficiaire = compteBeneficiaire;
  }

}
