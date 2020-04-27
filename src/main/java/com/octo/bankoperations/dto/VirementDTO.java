package com.octo.bankoperations.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VirementDTO implements Serializable {
  private Long id;
  private String ribEmetteur;
  private String ribBeneficiaire;
  private String motif;
  private BigDecimal amount;
  private Date date;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
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
