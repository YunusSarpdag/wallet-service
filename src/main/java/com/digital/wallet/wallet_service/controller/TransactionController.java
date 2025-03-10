package com.digital.wallet.wallet_service.controller;


import com.digital.wallet.wallet_service.dto.ApproveOrRejectRequest;
import com.digital.wallet.wallet_service.dto.PaymentRequest;
import com.digital.wallet.wallet_service.model.entity.Transaction;
import com.digital.wallet.wallet_service.model.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * It is a POST endpoint that performs the user's deposit transaction.
     * @param paymentRequest
     * @return
     */
    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody PaymentRequest paymentRequest){
        try {
            transactionService.deposit(paymentRequest);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     *It is the GET endpoint that lists all transaction history for the given wallet ID.
     * @param walletId
     * @return
     */
    @GetMapping("/listTransactionForWallet")
    public List<Transaction> listTransactionForWallet(@RequestParam(name = "walletId")Long walletId){
        return transactionService.getAllTransactionForWallet(walletId);
    }

    /**
     * It is a POST endpoint that performs the user's withdrawal transaction.
     * @param paymentRequest
     * @return
     */
    @PostMapping("/withDraw")
    public ResponseEntity withDraw(@RequestBody PaymentRequest paymentRequest){
        try {
            transactionService.withDraw(paymentRequest);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * It is a POST endpoint that performs the user's transaction approval or rejection.
     * @param paymentRequest
     * @return
     */
    @PostMapping("/approve")
    public ResponseEntity approveOrReject(@RequestBody ApproveOrRejectRequest paymentRequest){
        try {
            transactionService.approveOrReject(paymentRequest);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
