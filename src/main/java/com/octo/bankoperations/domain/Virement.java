package com.octo.bankoperations.domain;

import com.octo.bankoperations.enums.VirementStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "VIREMENT")
public class Virement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String reference;

    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal montantVirement;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExecution;

    @Column(length = 200)
    private String motifVirement;

    @Column(length = 24)
    private String ribBeneficiaire;

    @Column(length = 24)
    private String ribEmetteur;

    @Column
    private Date dateUpdateStatus;

    @Enumerated(value = EnumType.STRING)
    private VirementStatus status;

    public VirementStatus getStatus() {
        return status;
    }

    public void setStatus(VirementStatus status) {
        this.status = status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String referance) {
        this.reference = referance;
    }

    public BigDecimal getMontantVirement() {
        return montantVirement;
    }

    public void setMontantVirement(BigDecimal montantVirement) {
        this.montantVirement = montantVirement;
    }

    public Date getDateExecution() {
        return dateExecution;
    }

    public void setDateExecution(Date dateExecution) {
        this.dateExecution = dateExecution;
    }

    public String getMotifVirement() {
        return motifVirement;
    }

    public void setMotifVirement(String motifVirement) {
        this.motifVirement = motifVirement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRibBeneficiaire(String nrCompteBeneficiaire) {
        this.ribBeneficiaire = nrCompteBeneficiaire;
    }

    public String getRibBeneficiaire() {
        return ribBeneficiaire;
    }

    public Date getDateUpdateStatus() {
        return dateUpdateStatus;
    }

    public void setDateUpdateStatus(Date dateUpdateStatus) {
        this.dateUpdateStatus = dateUpdateStatus;
    }

    public String getRibEmetteur() {
        return ribEmetteur;
    }

    public void setRibEmetteur(String ribEmetteur) {
        this.ribEmetteur = ribEmetteur;
    }
}
