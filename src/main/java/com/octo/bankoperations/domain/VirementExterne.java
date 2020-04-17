package com.octo.bankoperations.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class VirementExterne extends Virement {

    @Column(length = 24)
    private String nrCompteBeneficiaire;

    @Column
    private boolean validated;

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public void setNrCompteBeneficiaire(String nrCompteBeneficiaire) {
        this.nrCompteBeneficiaire = nrCompteBeneficiaire;
    }

    @Override
    public String getNrCompteBeneficiaire() {
        return nrCompteBeneficiaire;
    }
}
