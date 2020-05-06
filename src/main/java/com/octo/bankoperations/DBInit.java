package com.octo.bankoperations;

import com.octo.bankoperations.domain.*;
import com.octo.bankoperations.enums.Gender;
import com.octo.bankoperations.enums.VirementStatus;
import com.octo.bankoperations.repository.CompteRepository;
import com.octo.bankoperations.repository.UtilisateurRepository;
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
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private VirementRepository virementRepository;

    @Override
    public void run(String... args) throws Exception {
        Adresse adresse1 = new Adresse();
        adresse1.setVille("Casa");
        adresse1.setAdresse1("Maarif");

        Adresse adresse2 = new Adresse();
        adresse2.setVille("Rabat");
        adresse2.setAdresse1("Agdal");

        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setUsername("user1");
        utilisateur1.setLastname("last1");
        utilisateur1.setFirstname("first1");
        utilisateur1.setGender(Gender.MALE);
        utilisateur1.setAdresse(adresse1);
        utilisateur1.setBirthdate(new Date());
        utilisateur1.setEmail("user1@gmail.com");

        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setUsername("user2");
        utilisateur2.setLastname("last2");
        utilisateur2.setFirstname("first2");
        utilisateur2.setGender(Gender.FEMALE);
        utilisateur2.setAdresse(adresse2);
        utilisateur2.setBirthdate(new Date());
        utilisateur2.setEmail("user2@gmail.com");

        Compte compte1 = new Compte();
        compte1.setRib("007787009200000000000000");
        compte1.setSolde(BigDecimal.valueOf(200000L));
        compte1.setUtilisateur(utilisateur1);

        utilisateur1.setCompte(compte1);

        Compte compte2 = new Compte();
        compte2.setRib("007787009200000000000001");
        compte2.setSolde(BigDecimal.valueOf(140000L));
        compte2.setUtilisateur(utilisateur2);

        utilisateur2.setCompte(compte2);

        utilisateurRepository.save(utilisateur1);
        utilisateurRepository.save(utilisateur2);
        compteRepository.save(compte2);
        compteRepository.save(compte1);

        Virement virement1 = new Virement();
        virement1.setRibEmetteur(compte1.getRib());
        virement1.setDateExecution(new Date());
        virement1.setMontantVirement(BigDecimal.valueOf(122));
        virement1.setMotifVirement("Motif idk");
        virement1.setRibBeneficiaire(compte2.getRib());
        virement1.setReference(VirementUtils.generateReference());
        virement1.setStatus(VirementStatus.INTERNE_NOT_SAVED_IN_CORDA);

        Virement virement2 = new Virement();
        virement2.setRibEmetteur(compte1.getRib());
        virement2.setDateExecution(new Date());
        virement2.setMontantVirement(BigDecimal.valueOf(1322));
        virement2.setMotifVirement("Motif ext");
        virement2.setRibBeneficiaire("444435444443");
        virement2.setReference(VirementUtils.generateReference());
        virement2.setStatus(VirementStatus.INTERNE_NOT_SAVED_IN_CORDA);

        virementRepository.saveAll(Arrays.asList(virement1, virement2));
    }
}
