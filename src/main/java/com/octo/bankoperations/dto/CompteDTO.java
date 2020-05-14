package com.octo.bankoperations.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class CompteDTO {
    private Long id;

    @Pattern(regexp = "\\d+", message = "RIB Should contain digits only")
    @Size(min = 24, max = 24, message = "RIB should be 24 digits long")
    private String rib;

    @NotNull(message = "Solde cannot be null")
    private BigDecimal solde;

    private boolean blocked;

    @NotNull(message = "Gender cannot be null")
    private Long clientId;

    public CompteDTO() {
    }

    public CompteDTO(Long id, String rib, BigDecimal solde, boolean blocked, Long clientId) {
        this.id = id;
        this.rib = rib;
        this.solde = solde;
        this.blocked = blocked;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompteDTO compteDTO = (CompteDTO) o;
        return blocked == compteDTO.blocked &&
                Objects.equals(id, compteDTO.id) &&
                Objects.equals(rib, compteDTO.rib) &&
                Objects.equals(solde, compteDTO.solde) &&
                Objects.equals(clientId, compteDTO.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rib, solde, blocked, clientId);
    }
}
