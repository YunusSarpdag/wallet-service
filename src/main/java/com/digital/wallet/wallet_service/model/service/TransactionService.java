package com.digital.wallet.wallet_service.model.service;

import com.digital.wallet.wallet_service.dto.ApproveOrRejectRequest;
import com.digital.wallet.wallet_service.dto.PaymentRequest;
import com.digital.wallet.wallet_service.model.entity.Transaction;
import com.digital.wallet.wallet_service.model.entity.Wallet;
import com.digital.wallet.wallet_service.model.entity.enums.OppositePartyType;
import com.digital.wallet.wallet_service.model.entity.enums.TransactionStatus;
import com.digital.wallet.wallet_service.model.entity.enums.TransactionType;
import com.digital.wallet.wallet_service.model.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;

    /**
     * This method receives the payment request, creates the transaction, and updates the wallet balance.
     * If the wallet is not active for shopping, an error is thrown. In addition, the transaction status is determined according to the transaction amount
     * and the transaction is saved.
     * @param paymentRequest
     */
    @Transactional
    public void deposit(PaymentRequest paymentRequest){
        Transaction transaction = createTransaction(paymentRequest);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        Wallet wallet = walletService.getWalletById(paymentRequest.getWalletId());
        if(wallet.getActiveForShopping() == null || !wallet.getActiveForShopping()){
            throw new RuntimeException("Wallet is not allowed for shopping");
        }

        BigDecimal walletBalance = wallet.getBalance();
        BigDecimal walletUsableBalance = wallet.getUsableBalance();
        wallet.setBalance(walletBalance.subtract(paymentRequest.getAmount()));
        wallet.setLastUpdatedOn(Instant.now());
        if (paymentRequest.getAmount().compareTo(BigDecimal.valueOf(1000))<=0){
            transaction.setTransactionStatus(TransactionStatus.APPROVED);
            wallet.setUsableBalance(walletUsableBalance.subtract(paymentRequest.getAmount()));
        }else{
            transaction.setTransactionStatus(TransactionStatus.PENDING);
        }
        transactionRepository.save(transaction);
        walletService.updateWallet(wallet);
    }

    /**
     * This method receives the payment request, creates the transaction, and updates the wallet balance.
     * If the wallet is not active for withdrawal, an error is thrown. Also, the transaction status is determined according to the transaction amount
     * and the transaction is saved.
     * @param paymentRequest
     */
    @Transactional
    public void withDraw(PaymentRequest paymentRequest){
        Transaction transaction = createTransaction(paymentRequest);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        Wallet wallet = walletService.getWalletById(paymentRequest.getWalletId());
        if(wallet.getActiveForShopping() == null || !wallet.getActiveForWithdraw()){
            throw new RuntimeException("Wallet is not allowed for withdraw");
        }
        //checkUser(wallet.getCustomerId());

        BigDecimal walletBalance = wallet.getBalance();
        BigDecimal walletUsableBalance = wallet.getUsableBalance();
        wallet.setBalance(walletBalance.add(paymentRequest.getAmount()));
        wallet.setLastUpdatedOn(Instant.now());
        if (paymentRequest.getAmount().compareTo(BigDecimal.valueOf(1000))<=0){
            transaction.setTransactionStatus(TransactionStatus.APPROVED);
            wallet.setUsableBalance(walletUsableBalance.add(paymentRequest.getAmount()));
        }else{
            transaction.setTransactionStatus(TransactionStatus.PENDING);
        }
        transactionRepository.save(transaction);
        walletService.updateWallet(wallet);
    }

    /**
     * This method retrieves all transaction records for the given wallet ID from the database.
     * Using `transactionRepository.findByWalletId(walletId)`, transactions associated with the given wallet ID are queried.
     * @param walletId
     * @return
     */
    public List<Transaction> getAllTransactionForWallet(Long walletId){
        return transactionRepository.findByWalletId(walletId);
    }

    /**
     * This method finds the transaction by the specified transaction ID and updates the transaction status to "APPROVED" or "DENIED".
     * If the transaction type is "DEPOSIT", the available balance of the wallet is reduced by the amount deposited.
     * If the transaction type is "WITHDRAW", the available balance of the wallet is increased by the amount withdrawn.
     * @param approveOrRejectRequest
     */
    @Transactional
    public void approveOrReject(ApproveOrRejectRequest approveOrRejectRequest){
        Optional<Transaction> transactionOptional = transactionRepository.findById(approveOrRejectRequest.getTransactionId());
        if(transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            if(transaction.getTransactionStatus().equals(TransactionStatus.PENDING)){
                Wallet wallet = walletService.getWalletById(transaction.getWalletId());
                BigDecimal walletUsableBalance = wallet.getUsableBalance();
                BigDecimal walletBalance = wallet.getBalance();
                if(TransactionStatus.contains(approveOrRejectRequest.getStatus())){
                    if(approveOrRejectRequest.getStatus().equals(TransactionStatus.APPROVED.name())){
                        transaction.setTransactionStatus(TransactionStatus.APPROVED);
                        transaction.setLastUpdatedOn(Instant.now());
                        if(transaction.getTransactionType().equals(TransactionType.DEPOSIT)){
                            wallet.setUsableBalance(walletUsableBalance.subtract(transaction.getAmount()));
                        }else{
                            wallet.setUsableBalance(walletUsableBalance.add(transaction.getAmount()));
                        }
                        wallet.setLastUpdatedOn(Instant.now());
                        transactionRepository.save(transaction);
                        walletService.updateWallet(wallet);
                    }else{
                        transaction.setTransactionStatus(TransactionStatus.DENIED);
                        transaction.setLastUpdatedOn(Instant.now());
                        if(transaction.getTransactionType().equals(TransactionType.DEPOSIT)){
                            wallet.setBalance(walletBalance.add(transaction.getAmount()));
                        }else{
                            wallet.setUsableBalance(walletBalance.subtract(transaction.getAmount()));
                        }
                        transactionRepository.save(transaction);
                        walletService.updateWallet(wallet);
                    }
                }else{
                    throw new RuntimeException("Transaction status must be APPROVED or DENIED");
                }
            }else{
                throw new RuntimeException("Transaction status is not pending");
            }
        }else {
            throw new RuntimeException("Transaction not found");
        }
    }

    private Transaction createTransaction(PaymentRequest paymentRequest){
        Transaction transaction = new Transaction();
        transaction.setAmount(paymentRequest.getAmount());
        transaction.setWalletId(paymentRequest.getWalletId());
        transaction.setOppositeParty(paymentRequest.getSource());
        transaction.setOppositeParty(paymentRequest.getSource());
        if (paymentRequest.getSource().length() == 26) {
            transaction.setOppositePartyType(OppositePartyType.IBAN);
        }else{
            transaction.setOppositePartyType(OppositePartyType.PAYMENT);
        }
        return transaction;
    }


}
