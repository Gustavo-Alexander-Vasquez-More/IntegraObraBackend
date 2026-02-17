package com.integraobra.integraApi.repository;

import com.integraobra.integraApi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
     boolean existsByEmail(String email);
     boolean existsByPhone(String phone);
    boolean existsByName(String name);
}
