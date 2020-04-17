package com.octo.bankoperations.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Inheritance
@Entity
@Table(name = "VIREMENT")
public abstract class Virement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal montantVirement;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExecution;

    @ManyToOne
    private Compte compteEmetteur;

    @Column(length = 200)
    private String motifVirement;

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

    public Compte getCompteEmetteur() {
        return compteEmetteur;
    }

    public void setCompteEmetteur(Compte compteEmetteur) {
        this.compteEmetteur = compteEmetteur;
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

    public abstract String getNrCompteBeneficiaire();
}
