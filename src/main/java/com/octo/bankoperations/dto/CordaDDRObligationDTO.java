package com.octo.bankoperations.dto;

import com.octo.bankoperations.enums.DDRObligationStatus;
import com.octo.bankoperations.enums.DDRObligationType;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class CordaDDRObligationDTO implements Serializable {
    private String externalId;

    private String issuer;

    private Date requesterDate;

    private String requester;

    private long amount;

    private String currency;

    private String owner;

    private DDRObligationType type;

    private DDRObligationStatus status;

    private UUID linearId;

    public CordaDDRObligationDTO() {
    }

    public CordaDDRObligationDTO(String externalId, String issuer, Date requesterDate, String requester, long amount,
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

    public long getAmount() {
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

    public void setAmount(long amount) {
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
}
