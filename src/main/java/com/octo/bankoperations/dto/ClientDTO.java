package com.octo.bankoperations.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.bankoperations.enums.Gender;

import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO {
    private Long id;

    private String username;

    private Gender gender;

    private String lastname;

    private String firstname;

    private String email;

    private String ville;

    private String adresse1;

    private String adresse2;

    private Date birthdate;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String username, Gender gender, String lastname, String firstname, String email, String ville,
                     String adresse1, String adresse2, Date birthdate) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.ville = ville;
        this.adresse1 = adresse1;
        this.adresse2 = adresse2;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse1() {
        return adresse1;
    }

    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }

    public String getAdresse2() {
        return adresse2;
    }

    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(id, clientDTO.id) &&
                Objects.equals(username, clientDTO.username) &&
                gender == clientDTO.gender &&
                Objects.equals(lastname, clientDTO.lastname) &&
                Objects.equals(firstname, clientDTO.firstname) &&
                Objects.equals(email, clientDTO.email) &&
                Objects.equals(ville, clientDTO.ville) &&
                Objects.equals(adresse1, clientDTO.adresse1) &&
                Objects.equals(adresse2, clientDTO.adresse2) &&
                Objects.equals(birthdate, clientDTO.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, gender, lastname, firstname, email, ville, adresse1, adresse2, birthdate);
    }
}
