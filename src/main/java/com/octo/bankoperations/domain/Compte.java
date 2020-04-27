package com.octo.bankoperations.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "COMPTE")
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 24, unique = true)
    private String rib;

    @Column(precision = 16, scale = 2)
    private BigDecimal solde;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
