package com.digital.wallet.wallet_service.model.entity;

import com.digital.wallet.wallet_service.model.entity.enums.OppositePartyType;
import com.digital.wallet.wallet_service.model.entity.enums.TransactionStatus;
import com.digital.wallet.wallet_service.model.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends BaseModel{
    private Long walletId;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String oppositeParty;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Enumerated(EnumType.STRING)
    private OppositePartyType oppositePartyType;
}
