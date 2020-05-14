package com.octo.bankoperations.dto;

import com.octo.bankoperations.enums.DDRObligationStatus;
import com.octo.bankoperations.enums.DDRObligationType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class CordaDDRObligationDTO implements Serializable {
    @NotBlank
    private String externalId;

    @NotBlank
    private String issuer;

    @NotNull
    private Date requesterDate;

    @NotBlank
    private String requester;

    private @NotNull Long amount;

    private String currency;

    @NotNull
    private String owner;

    @NotNull
    private DDRObligationType type;

    @NotNull
    private DDRObligationStatus status;

    @NotNull
    private UUID linearId;

    public CordaDDRObligationDTO() {
    }

    public CordaDDRObligationDTO(String externalId, String issuer, Date requesterDate, String requester, Long amount,
                                 String currency, String owner, DDRObligationType type, DDRObligationStatus status, UUID linearId) {
        this.externalId = externalId;
        this.issuer = issuer;
        this.requesterDate = requesterDate;
        this.requester = requester;
        this.amount = amount;
        this.currency = currency;
        this.owner = owner;
        this.type = type;
        this.status = status;
        this.linearId = linearId;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getIssuer() {
        return issuer;
    }

    public Date getRequesterDate() {
        return requesterDate;
    }

    public String getRequester() {
        return requester;
    }

    public @NotNull Long getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOwner() {
        return owner;
    }

    public DDRObligationType getType() {
        return type;
    }

    public DDRObligationStatus getStatus() {
        return status;
    }

    public UUID getLinearId() {
        return linearId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public void setRequesterDate(Date requesterDate) {
        this.requesterDate = requesterDate;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public void setAmount(@NotNull Long amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setType(DDRObligationType type) {
        this.type = type;
    }

    public void setStatus(DDRObligationStatus status) {
        this.status = status;
    }

    public void setLinearId(UUID linearId) {
        this.linearId = linearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CordaDDRObligationDTO that = (CordaDDRObligationDTO) o;
        return Objects.equals(externalId, that.externalId) &&
                Objects.equals(issuer, that.issuer) &&
                Objects.equals(requesterDate, that.requesterDate) &&
                Objects.equals(requester, that.requester) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(owner, that.owner) &&
                type == that.type &&
                status == that.status &&
                Objects.equals(linearId, that.linearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId, issuer, requesterDate, requester, amount, currency, owner, type, status, linearId);
    }
}
