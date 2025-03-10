package com.digital.wallet.wallet_service.dto;

import com.digital.wallet.wallet_service.model.entity.enums.Currency;
import lombok.Data;

@Data
public class WalletDto {
    private String walletName;
    private String currency;
    private Boolean activeForShopping;
    private Boolean activeForWithdraw;
    // I added customerId due to assign wallet to customer
    private Long customerId;
}
