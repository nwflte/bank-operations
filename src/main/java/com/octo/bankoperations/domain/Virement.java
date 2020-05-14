package com.octo.bankoperations.domain;

import com.octo.bankoperations.enums.VirementStatus;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "VIREMENT")
public class Virement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String reference;

    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExecution;

    @Column(length = 200)
    private String motif;

    @Pattern(regexp = "\\d+", message = "RIB Should contain digits only")
    @Size(min = 24, max = 24, message = "RIB should be 24 digits long")
    @Column(length = 24, nullable = false, updatable = false)
    private String ribBeneficiaire;

    @Pattern(regexp = "\\d+", message = "RIB Should contain digits only")
    @Size(min = 24, max = 24, message = "RIB should be 24 digits long")
    @Column(length = 24, nullable = false, updatable = false)
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal montantVirement) {
        this.amount = montantVirement;
    }

    public Date getDateExecution() {
        return dateExecution;
    }

    public void setDateExecution(Date dateExecution) {
        this.dateExecution = dateExecution;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motifVirement) {
        this.motif = motifVirement;
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
