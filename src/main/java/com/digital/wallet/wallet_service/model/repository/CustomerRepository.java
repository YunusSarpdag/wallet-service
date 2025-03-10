package com.digital.wallet.wallet_service.model.repository;

import com.digital.wallet.wallet_service.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByTckn(String tckn);
}
