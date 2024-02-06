package com.boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.CustomerModel;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long>{
    boolean existsByName(String name);
    boolean existsByCpf(String cpf);
    Optional<CustomerModel> findById(Long id);
}
