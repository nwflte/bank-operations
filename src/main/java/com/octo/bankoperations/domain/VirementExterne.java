package com.octo.bankoperations.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class VirementExterne extends Virement {

    @Column
    private boolean validated;

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

}
