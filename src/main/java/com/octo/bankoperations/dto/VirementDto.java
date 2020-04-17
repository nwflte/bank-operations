package com.octo.bankoperations.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VirementDto implements Serializable {
  private String ribEmetteur;
  private String ribBeneficiaire;
  private String motif;
  private BigDecimal montantVirement;
  private Date date;

  public String getRibEmetteur() {
    return ribEmetteur;
  }

  public void setRibEmetteur(String ribEmetteur) {
    this.ribEmetteur = ribEmetteur;
  }

  public String getRibBeneficiaire() {
    return ribBeneficiaire;
  }

  public void setRibBeneficiaire(String ribBeneficiaire) {
    this.ribBeneficiaire = ribBeneficiaire;
  }

  public BigDecimal getMontantVirement() {
    return montantVirement;
  }

  public void setMontantVirement(BigDecimal montantVirement) {
    this.montantVirement = montantVirement;
  }

  public String getMotif() {
    return motif;
  }

  public void setMotif(String motif) {
    this.motif = motif;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
