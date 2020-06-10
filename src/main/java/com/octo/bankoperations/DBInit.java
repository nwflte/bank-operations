package com.octo.bankoperations;

import com.octo.bankoperations.domain.Adresse;
import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.domain.Virement;
import com.octo.bankoperations.enums.Gender;
import com.octo.bankoperations.enums.VirementStatus;
import com.octo.bankoperations.repository.ClientRepository;
import com.octo.bankoperations.repository.CompteRepository;
import com.octo.bankoperations.repository.VirementRepository;
import com.octo.bankoperations.utils.VirementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DBInit implements CommandLineRunner {

    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    Constants constants;

    @Override
    public void run(String... args) throws Exception {
        Adresse adresse1 = new Adresse();
        adresse1.setVille("Casa");
        adresse1.setAdresse1("Maarif");

        Adresse adresse2 = new Adresse();
        adresse2.setVille("Rabat");
        adresse2.setAdresse1("Agdal");

        Client client1 = new Client();
        client1.setUsername("user1");
        client1.setLastname("last1");
        client1.setFirstname("first1");
        client1.setGender(Gender.MALE);
        client1.setAdresse(adresse1);
        client1.setBirthdate(new Date());
        client1.setEmail("user1@gmail.com");

        Client client2 = new Client();
        client2.setUsername("user2");
        client2.setLastname("last2");
        client2.setFirstname("first2");
        client2.setGender(Gender.FEMALE);
        client2.setAdresse(adresse2);
        client2.setBirthdate(new Date());
        client2.setEmail("user2@gmail.com");

        Compte compte1 = new Compte();
        compte1.setRib(constants.getBankCode() + "787009200000000000000");
        compte1.setSolde(BigDecimal.valueOf(200000L));
        compte1.setClient(client1);

        client1.setCompte(compte1);

        Compte compte2 = new Compte();
        compte2.setRib(constants.getBankCode() + "787009200000000000001");
        compte2.setSolde(BigDecimal.valueOf(140000L));
        compte2.setClient(client2);

        client2.setCompte(compte2);

        clientRepository.save(client1);
        clientRepository.save(client2);
        compteRepository.save(compte2);
        compteRepository.save(compte1);

        /*Virement virement1 = new Virement();
        virement1.setRibEmetteur(compte1.getRib());
        virement1.setDateExecution(new Date());
        virement1.setAmount(BigDecimal.valueOf(122));
        virement1.setMotif("Motif idk");
        virement1.setRibBeneficiaire(compte2.getRib());
        virement1.setReference(VirementUtils.generateReference());
        virement1.setStatus(VirementStatus.INTERNE_PENDING_SAVE_IN_CORDA);

        Virement virement2 = new Virement();
        virement2.setRibEmetteur(compte1.getRib());
        virement2.setDateExecution(new Date());
        virement2.setAmount(BigDecimal.valueOf(1322));
        virement2.setMotif("Motif ext");
        virement2.setRibBeneficiaire("007787009200000000000002");
        virement2.setReference(VirementUtils.generateReference());
        virement2.setStatus(VirementStatus.INTERNE_PENDING_SAVE_IN_CORDA);

        virementRepository.saveAll(Arrays.asList(virement1, virement2));*/
    }
}
