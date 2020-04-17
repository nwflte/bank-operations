package com.octo.bankoperations.domain;

import javax.persistence.*;

@Entity
@Table(name = "BANK_INFO")
public class BankInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 3, unique = true)
    private String code;

    @Column(unique = true)
    private String nom;

}
