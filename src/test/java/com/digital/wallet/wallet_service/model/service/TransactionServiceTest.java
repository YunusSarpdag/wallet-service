package com.digital.wallet.wallet_service.model.service;

import com.digital.wallet.wallet_service.dto.ApproveOrRejectRequest;
import com.digital.wallet.wallet_service.dto.PaymentRequest;
import com.digital.wallet.wallet_service.model.entity.Transaction;
import com.digital.wallet.wallet_service.model.entity.Wallet;
import com.digital.wallet.wallet_service.model.entity.enums.TransactionStatus;
import com.digital.wallet.wallet_service.model.entity.enums.TransactionType;
import com.digital.wallet.wallet_service.model.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionRepository transactionRepository;

    private PaymentRequest paymentRequest;

    private Wallet wallet;

    private ApproveOrRejectRequest request;

    private Transaction transaction;

    @BeforeEach
    public void setUp(){
        paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(BigDecimal.TEN);
        paymentRequest.setWalletId(1L);
        paymentRequest.setSource("18");
        wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUsableBalance(BigDecimal.ZERO);
        wallet.setActiveForShopping(true);
        wallet.setActiveForWithdraw(true);
        request = new ApproveOrRejectRequest();
        request.setTransactionId(1L);
        request.setStatus("APPROVED");
        transaction= new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.TEN);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionStatus(TransactionStatus.PENDING);
    }

    @Test
    void testDeposit() {
        when(walletService.getWalletById(any())).thenReturn(wallet);
        transactionService.deposit(paymentRequest);
        verify(walletService, times(1)).updateWallet(any());
    }

    @Test
    void testDeposit_NotActiveForShopping() {
        wallet.setActiveForShopping(false);
        when(walletService.getWalletById(any())).thenReturn(wallet);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.deposit(paymentRequest);
        });
        String expectedMessage = "Wallet is not allowed for shopping";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testWithDraw(){
        when(walletService.getWalletById(any())).thenReturn(wallet);
        transactionService.withDraw(paymentRequest);
        verify(walletService, times(1)).updateWallet(any());
    }

    @Test
    void testDeposit_NotActiveForWithDraw() {
        wallet.setActiveForWithdraw(false);
        when(walletService.getWalletById(any())).thenReturn(wallet);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.withDraw(paymentRequest);
        });
        String expectedMessage = "Wallet is not allowed for withdraw";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testApproveOrReject(){
        when(walletService.getWalletById(any())).thenReturn(wallet);
        when(transactionRepository.findById(any())).thenReturn(Optional.of(transaction));
        transactionService.approveOrReject(request);
    }

    @Test
    void testApproveOrReject_notExistingTransaction() {
        request.setTransactionId(2L);
        when(transactionRepository.findById(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.approveOrReject(request);
        });
        String expectedMessage = "Transaction not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testApproveOrReject_transactionStatusIsNotPending() {
        transaction.setTransactionStatus(TransactionStatus.APPROVED);
        when(transactionRepository.findById(any())).thenReturn(Optional.of(transaction));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.approveOrReject(request);
        });
        String expectedMessage = "Transaction status is not pending";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testApproveOrReject_differentTransactionStatusInput() {
        request.setStatus("NONEXIST");
        when(transactionRepository.findById(any())).thenReturn(Optional.of(transaction));
        when(walletService.getWalletById(any())).thenReturn(wallet);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transactionService.approveOrReject(request);
        });
        String expectedMessage = "Transaction status must be APPROVED or DENIED";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}