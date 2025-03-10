package com.digital.wallet.wallet_service.model.entity;

import com.digital.wallet.wallet_service.model.entity.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Wallet extends BaseModel{
    private String walletName;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private Boolean activeForShopping;
    private Boolean activeForWithdraw;
    private BigDecimal balance;
    private BigDecimal usableBalance;
    private String iban;
    //@OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Transaction> transactions;
    private Long customerId;
}
