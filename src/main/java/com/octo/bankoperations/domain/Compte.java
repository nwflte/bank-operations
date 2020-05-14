package com.octo.bankoperations.domain;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "COMPTE")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Pattern(regexp = "\\d+", message = "RIB Should contain digits only")
    @Size(min = 24, max = 24, message = "RIB should be 24 digits long")
    @Column(unique = true, updatable = false, nullable = false)
    private String rib;

    @Column(precision = 16, scale = 2, nullable = false)
    private BigDecimal solde;

    @ManyToOne
    @JoinColumn(name = "client_id", unique = true)
    private Client client;

    @Column
    private boolean blocked;

    public String getRib() {
        return rib;
    }

    public void setRib(String nrCompte) {
        this.rib = nrCompte;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
