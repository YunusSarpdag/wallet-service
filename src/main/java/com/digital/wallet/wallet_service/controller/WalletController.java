package com.digital.wallet.wallet_service.controller;


import com.digital.wallet.wallet_service.dto.WalletDto;
import com.digital.wallet.wallet_service.dto.WalletListRequest;
import com.digital.wallet.wallet_service.model.entity.Wallet;
import com.digital.wallet.wallet_service.model.service.CustomerService;
import com.digital.wallet.wallet_service.model.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;
    private final CustomerService customerService;
    /**
     * It is a POST endpoint that creates a new wallet for the user.
     * @param input
     * @return
     */
    @PostMapping("/creteWallet")
    public ResponseEntity bookHour(@RequestBody WalletDto input){
        try {
            walletService.createWallet(input);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * It is a POST endpoint that lists all wallets for the user.
     * @param walletListRequest
     * @return
     */
    @PostMapping("/listWallet")
    public ResponseEntity<List<Wallet>> getAllWalletForUser(@RequestBody WalletListRequest walletListRequest){
        try {
            List<Wallet>  walletList = walletService.getAllWalletForCustomer(walletListRequest);
            return ResponseEntity.ok(walletList);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
