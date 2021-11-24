package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.FrequentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FrequentAccountRepository extends JpaRepository<FrequentAccount, Long> {
}
