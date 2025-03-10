package com.digital.wallet.wallet_service.model.repository;

import com.digital.wallet.wallet_service.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByIban(String day);
    List<Wallet> findByCustomerId(Long customerId);
}
