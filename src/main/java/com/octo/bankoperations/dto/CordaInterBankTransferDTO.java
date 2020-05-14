package com.octo.bankoperations.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class CordaInterBankTransferDTO implements Serializable {

    @Pattern(regexp = "\\d+", message = "RIB Should contain digits only")
    @Size(min = 24, max = 24, message = "RIB should be 24 digits long")
    private String senderRIB;

    @Pattern(regexp = "\\d+", message = "RIB Should contain digits only")
    @Size(min = 24, max = 24, message = "RIB should be 24 digits long")
    private String receiverRIB;

    @NotBlank
    private String senderBank;

    @NotBlank
    private String receiverBank;

    @NotNull
    private long amount;

    private String currency;

    @NotNull
    private Date executionDate;

    @NotBlank
    private String externalId;

    @NotNull
    private UUID linearId;

    public CordaInterBankTransferDTO() {
    }

    public CordaInterBankTransferDTO(String senderRIB, String receiverRIB, String senderBank, String receiverBank, long amount,
                                     String currency, Date executionDate, String externalId, UUID linearId) {
        this.senderRIB = senderRIB;
        this.receiverRIB = receiverRIB;
        this.senderBank = senderBank;
        this.receiverBank = receiverBank;
        this.amount = amount;
        this.currency = currency;
        this.executionDate = executionDate;
        this.externalId = externalId;
        this.linearId = linearId;
    }

    public String getSenderRIB() {
        return senderRIB;
    }

    public String getReceiverRIB() {
        return receiverRIB;
    }

    public String getSenderBank() {
        return senderBank;
    }

    public String getReceiverBank() {
        return receiverBank;
    }

    public long getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public String getExternalId() {
        return externalId;
    }

    public UUID getLinearId() {
        return linearId;
    }

    public void setSenderRIB(String senderRIB) {
        this.senderRIB = senderRIB;
    }

    public void setReceiverRIB(String receiverRIB) {
        this.receiverRIB = receiverRIB;
    }

    public void setSenderBank(String senderBank) {
        this.senderBank = senderBank;
    }

    public void setReceiverBank(String receiverBank) {
        this.receiverBank = receiverBank;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setLinearId(UUID linearId) {
        this.linearId = linearId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CordaInterBankTransferDTO that = (CordaInterBankTransferDTO) o;
        return amount == that.amount &&
                Objects.equals(senderRIB, that.senderRIB) &&
                Objects.equals(receiverRIB, that.receiverRIB) &&
                Objects.equals(senderBank, that.senderBank) &&
                Objects.equals(receiverBank, that.receiverBank) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(executionDate, that.executionDate) &&
                Objects.equals(externalId, that.externalId) &&
                Objects.equals(linearId, that.linearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderRIB, receiverRIB, senderBank, receiverBank, amount, currency, executionDate, externalId, linearId);
    }
}
