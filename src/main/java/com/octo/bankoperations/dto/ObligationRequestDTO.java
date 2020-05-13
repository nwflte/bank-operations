package com.octo.bankoperations.dto;

import java.util.Objects;

public class ObligationRequestDTO {
    private long amount;


    public ObligationRequestDTO() {
    }

    public ObligationRequestDTO(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObligationRequestDTO that = (ObligationRequestDTO) o;
        return amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
