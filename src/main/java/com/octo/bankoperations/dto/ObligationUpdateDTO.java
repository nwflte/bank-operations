package com.octo.bankoperations.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ObligationUpdateDTO {
    @NotBlank
    private String externalId;

    public ObligationUpdateDTO() {
    }

    public ObligationUpdateDTO(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObligationUpdateDTO that = (ObligationUpdateDTO) o;
        return Objects.equals(externalId, that.externalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId);
    }
}
