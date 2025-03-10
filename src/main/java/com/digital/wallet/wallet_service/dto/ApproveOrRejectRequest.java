package com.digital.wallet.wallet_service.dto;

import lombok.Data;

@Data
public class ApproveOrRejectRequest {
    private Long transactionId;
    private String status;
}
