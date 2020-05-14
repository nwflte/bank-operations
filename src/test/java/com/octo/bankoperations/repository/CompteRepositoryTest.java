package com.octo.bankoperations.repository;

import com.octo.bankoperations.domain.Client;
import com.octo.bankoperations.domain.Compte;
import com.octo.bankoperations.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompteRepositoryTest {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Client client1;
    private Client client2;
    private Compte compte1;
    private Compte compte2;

    @BeforeAll
    public void setup() {
        client1 = new Client();
        client1.setUsername("user1");
        client1.setLastname("last1");
        client1.setFirstname("first1");
        client1.setGender(Gender.MALE);
        client1.setEmail("user1@gmail.com");

        client2 = new Client();
        client2.setUsername("user2");
        client2.setLastname("last2");
        client2.setFirstname("first2");
        client2.setGender(Gender.FEMALE);
        client2.setEmail("user2@gmail.com");

        compte1 = new Compte();
        compte1.setRib("007787009200000000000000");
        compte1.setSolde(BigDecimal.valueOf(200000L));
        compte1.setClient(client1);
        client1.setCompte(compte1);

        compte2 = new Compte();
        compte2.setRib("007787009200000000000001");
        compte2.setSolde(BigDecimal.valueOf(140000L));
        compte2.setClient(client2);
        client2.setCompte(compte2);

        clientRepository.save(client1);
        clientRepository.save(client2);
        compteRepository.save(compte2);
        compteRepository.save(compte1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"007787009200000000000000", "007787009200000000000001"})
    void findByRib_ReturnsCompte(String rib) {
        Optional<Compte> actualResult = compteRepository.findByRib(rib);

        Assertions.assertTrue(actualResult.isPresent());
        Assertions.assertEquals(rib, actualResult.get().getRib());
    }

    @ParameterizedTest
    @ValueSource(strings = {"007787009200000000000099", "007787009200000000000088"})
    void findByBadRib_ReturnsEmptyOptional() {
        Optional<Compte> actualResult = compteRepository.findByRib("007787009200000000000099");

        Assertions.assertFalse(actualResult.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"007787009200000000000000", "007787009200000000000001"})
    void existsByRib_ReturnsTrue(String rib) {
        boolean actualResult = compteRepository.existsByRib(rib);

        Assertions.assertTrue(actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"007787009200000000000099", "007787009200000000000088"})
    void existsByBadRib_ReturnsFalse(String rib) {
        boolean actualResult = compteRepository.existsByRib(rib);

        Assertions.assertFalse(actualResult);
    }

    @Test
    void findByClient_ReturnsCompte() {
        Optional<Compte> actualResult = compteRepository.findByClient(client1);

        Assertions.assertTrue(actualResult.isPresent());
        Assertions.assertEquals(client1.getId(), actualResult.get().getClient().getId());
    }

    @Test
    void findByBadClient_ReturnsEmptyOptional() {
        Client client = new Client();
        client.setUsername("user1");
        client.setLastname("last1");
        client.setFirstname("first1");
        client.setGender(Gender.MALE);
        client.setEmail("user1@gmail.com");
        clientRepository.save(client);

        Optional<Compte> actualResult = compteRepository.findByClient(client);

        Assertions.assertFalse(actualResult.isPresent());
    }
}