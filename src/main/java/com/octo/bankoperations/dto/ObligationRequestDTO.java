package com.octo.bankoperations.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ObligationRequestDTO {
    private @NotNull Long amount;

    public ObligationRequestDTO() {
    }

    public ObligationRequestDTO(Long amount) {
        this.amount = amount;
    }

    public @NotNull Long getAmount() {
        return amount;
    }

    public void setAmount(@NotNull Long amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObligationRequestDTO that = (ObligationRequestDTO) o;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
