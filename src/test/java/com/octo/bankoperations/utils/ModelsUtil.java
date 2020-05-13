package com.octo.bankoperations.utils;

import com.octo.bankoperations.domain.Adresse;
import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.dto.CordaDDRObligationDTO;
import com.octo.bankoperations.dto.CordaInterBankTransferDTO;
import com.octo.bankoperations.dto.CordaIntraBankTransferDTO;
import com.octo.bankoperations.enums.DDRObligationStatus;
import com.octo.bankoperations.enums.DDRObligationType;
import com.octo.bankoperations.enums.Gender;
import com.octo.bankoperations.enums.VirementStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class ModelsUtil {

    public static Long VIREMENT_ID = 1L;
    public static String VIREMENT_REF = "REF454662314";

    public static Long CLIENT_ID = 1L;

    public static Virement createVirement() {
        final Virement virement = new Virement();
        virement.setReference(VIREMENT_REF);
        virement.setId(VIREMENT_ID);
        virement.setAmount(BigDecimal.valueOf(12));
        virement.setRibBeneficiaire("007111111111111111111111");
        virement.setRibEmetteur("007111111111111111111112");
        virement.setDateExecution(Date.from(Instant.parse("2020-05-01T15:23:01Z")));
        virement.setStatus(VirementStatus.INTERNE_PENDING_SAVE_IN_CORDA);
        virement.setMotif("Motif");
        return virement;
    }

    public static Virement createVirement(Long id, String reference, BigDecimal amount, Date dateExecution, String motif,
                                          String ribEmetteur, String ribBeneficiaire, Date dateUpdateStatus, VirementStatus status) {

        final Virement virement = new Virement();
        virement.setReference(reference);
        virement.setId(id);
        virement.setAmount(amount);
        virement.setRibBeneficiaire(ribBeneficiaire);
        virement.setRibEmetteur(ribEmetteur);
        virement.setDateExecution(dateExecution);
        virement.setStatus(status);
        virement.setMotif(motif);
        virement.setDateUpdateStatus(dateUpdateStatus);
        return virement;
    }

    public static Virement createVirement(String ribEmetteur, String ribBeneficiaire, BigDecimal montant) {
        final Virement virement = createVirement();
        if(montant != null) virement.setAmount(montant);
        if(ribBeneficiaire != null) virement.setRibBeneficiaire(ribBeneficiaire);
        if(ribEmetteur != null) virement.setRibEmetteur(ribEmetteur);
        return virement;
    }

    public static Client createClient(Long id) {
        final Client client = new Client();
        client.setId(id);
        client.setUsername("user1");
        client.setLastname("last1");
        client.setFirstname("first1");
        client.setGender(Gender.MALE);
        client.setAdresse(createAdresse());
        client.setBirthdate(new java.util.Date());
        client.setEmail("user1@gmail.com");
        return client;
    }

    private static Adresse createAdresse() {
        Adresse adresse = new Adresse();
        adresse.setVille("Casa");
        adresse.setAdresse1("Maarif");
        adresse.setAdresse2("Rue etc");
        return adresse;
    }

    public static Compte createCompte(String rib, Long clientId) {
        final Compte compte = new Compte();
        compte.setRib(rib);
        compte.setSolde(BigDecimal.valueOf(200000L));
        compte.setClient(createClient(clientId));
        return compte;
    }

    public static Compte createCompte(String rib, Long clientId, BigDecimal solde, Long compteId) {
        final Compte compte = createCompte(rib, clientId);
        if(solde != null) compte.setSolde(solde);
        if(compteId != null) compte.setId(compteId);
        return compte;
    }

    public static CordaDDRObligationDTO createObligation(DDRObligationType type, DDRObligationStatus status) {
        CordaDDRObligationDTO pledge = new CordaDDRObligationDTO();
        pledge.setAmount(12);
        pledge.setCurrency("MAD");
        pledge.setExternalId("KFKFKLLLDD");
        pledge.setIssuer("ISSUER");
        pledge.setOwner("OWNER");
        pledge.setLinearId(UUID.randomUUID());
        pledge.setRequester("REQUESTER");
        pledge.setRequesterDate(Date.from(Instant.parse("2020-05-01T15:23:01Z")));
        pledge.setStatus(status);
        pledge.setType(type);
        return pledge;
    }

    public static CordaDDRObligationDTO createObligation(DDRObligationType type,
                                                         DDRObligationStatus status,
                                                         String externalId) {
        CordaDDRObligationDTO pledge = createObligation(type, status);
        pledge.setExternalId(externalId);
        return pledge;
    }

    public static CordaIntraBankTransferDTO createIntraBankTransfer() {
        CordaIntraBankTransferDTO dto = new CordaIntraBankTransferDTO();
        dto.setAmount(100);
        dto.setBank("BankA");
        dto.setCurrency("MAD");
        dto.setExecutionDate(Date.from(Instant.parse("2020-05-01T15:23:01Z")));
        dto.setExternalId("TRANSFER_ID");
        dto.setLinearId(UUID.randomUUID());
        dto.setReceiverRIB("007111111111111111111111");
        dto.setSenderRIB("007111111111111111111112");
        return dto;
    }

    public static CordaIntraBankTransferDTO createIntraBankTransfer(String externalId) {
        CordaIntraBankTransferDTO dto = createIntraBankTransfer();
        dto.setExternalId(externalId);
        return dto;
    }

    public static CordaInterBankTransferDTO createInterBankTransfer() {
        CordaInterBankTransferDTO dto = new CordaInterBankTransferDTO();
        dto.setAmount(100);
        dto.setReceiverBank("BankB");
        dto.setSenderBank("BankA");
        dto.setCurrency("MAD");
        dto.setExecutionDate(Date.from(Instant.parse("2020-05-01T15:23:01Z")));
        dto.setExternalId("TRANSFER_ID");
        dto.setLinearId(UUID.randomUUID());
        dto.setReceiverRIB("007111111111111111111111");
        dto.setSenderRIB("007111111111111111111112");
        return dto;
    }

    public static CordaInterBankTransferDTO createInterBankTransfer(String externalId) {
        CordaInterBankTransferDTO dto = createInterBankTransfer();
        dto.setExternalId(externalId);
        return dto;
    }
}
