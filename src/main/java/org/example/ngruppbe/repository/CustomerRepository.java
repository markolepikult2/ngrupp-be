package org.example.ngruppbe.repository;

import org.example.ngruppbe.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

