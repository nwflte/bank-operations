package com.octo.bankoperations.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.octo.bankoperations.enums.VirementStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VirementDTO implements Serializable {
    private Long id;
    private String reference;

    @Pattern(regexp = "\\d+", message = "RIB Should contain digits only")
    @Size(min = 24, max = 24, message = "RIB should be 24 digits long")
    private String ribEmetteur;

    @Pattern(regexp = "\\d+", message = "RIB Should contain digits only")
    @Size(min = 24, max = 24, message = "RIB should be 24 digits long")
    private String ribBeneficiaire;

    @NotBlank
    private String motif;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Date dateExecution;

    private VirementStatus status;
    private Date dateUpdateStatus;

    public VirementDTO() {
    }

    public VirementDTO(Long id, String reference, String ribEmetteur, String ribBeneficiaire, String motif, BigDecimal amount,
                       Date dateExecution, VirementStatus status, Date dateUpdateStatus) {
        this.id = id;
        this.reference = reference;
        this.ribEmetteur = ribEmetteur;
        this.ribBeneficiaire = ribBeneficiaire;
        this.motif = motif;
        this.amount = amount;
        this.dateExecution = dateExecution;
        this.status = status;
        this.dateUpdateStatus = dateUpdateStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public Date getDateExecution() {
        return dateExecution;
    }

    public void setDateExecution(Date dateExecution) {
        this.dateExecution = dateExecution;
    }

    public VirementStatus getStatus() {
        return status;
    }

    public void setStatus(VirementStatus status) {
        this.status = status;
    }

    public Date getDateUpdateStatus() {
        return dateUpdateStatus;
    }

    public void setDateUpdateStatus(Date dateUpdateStatus) {
        this.dateUpdateStatus = dateUpdateStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirementDTO that = (VirementDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(ribEmetteur, that.ribEmetteur) &&
                Objects.equals(ribBeneficiaire, that.ribBeneficiaire) &&
                Objects.equals(motif, that.motif) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(dateExecution, that.dateExecution) &&
                status == that.status &&
                Objects.equals(dateUpdateStatus, that.dateUpdateStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, ribEmetteur, ribBeneficiaire, motif, amount, dateExecution, status, dateUpdateStatus);
    }
}
