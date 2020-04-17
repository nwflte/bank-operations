package com.octo.bankoperations.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankTransferDTO implements Serializable {
    private String senderRIB;
    private String receiverRIB;
    private BigDecimal amount;
    private Date executionDate;

    public BankTransferDTO() {
    }

    public BankTransferDTO(String senderRIB, String receiverRIB, BigDecimal amount, Date executionDate) {
        this.senderRIB = senderRIB;
        this.receiverRIB = receiverRIB;
        this.amount = amount;
        this.executionDate = executionDate;
    }

    public void setSenderRIB(String senderRIB) {
        this.senderRIB = senderRIB;
    }

    public void setReceiverRIB(String receiverRIB) {
        this.receiverRIB = receiverRIB;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public String getSenderRIB() {
        return senderRIB;
    }

    public String getReceiverRIB() {
        return receiverRIB;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getExecutionDate() {
        return executionDate;
    }
}
