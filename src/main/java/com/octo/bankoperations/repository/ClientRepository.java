package com.octo.bankoperations.repository;

import com.octo.bankoperations.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
