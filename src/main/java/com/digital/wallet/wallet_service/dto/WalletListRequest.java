package com.digital.wallet.wallet_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletListRequest {
    private Long customerId;
    private String currency;
    private BigDecimal amount;
}
