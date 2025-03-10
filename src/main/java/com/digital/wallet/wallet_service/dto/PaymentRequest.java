package com.digital.wallet.wallet_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private BigDecimal amount;
    private Long walletId;
    private String source;
}
