package com.digital.wallet.wallet_service.model.service;

import com.digital.wallet.wallet_service.dto.WalletDto;
import com.digital.wallet.wallet_service.dto.WalletListRequest;
import com.digital.wallet.wallet_service.model.entity.Customer;
import com.digital.wallet.wallet_service.model.entity.Wallet;
import com.digital.wallet.wallet_service.model.repository.WalletRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private CustomerService customerService;

    @Mock
    private IbanGenerator ibanGenerator;

    @Mock
    private WalletRepository walletRepository;

    @Test
    void testCreateWallet() {
        WalletDto walletDto = new WalletDto();
        walletDto.setWalletName("Test Wallet Name");
        walletDto.setCustomerId(1L);
        walletDto.setCurrency("EUR");
        walletDto.setActiveForShopping(true);
        walletDto.setActiveForWithdraw(true);
        Customer customer = new Customer();
        Wallet wallet = new Wallet();
        wallet.setWalletName(walletDto.getWalletName());
        customer.setId(1L);
        when(ibanGenerator.generateUniqueIban()).thenReturn("TR290536578321406852688691");
        when(customerService.getCustomerById(any())).thenReturn(customer);
        when(walletRepository.save(any())).thenReturn(wallet);
        walletService.createWallet(walletDto);
        assertEquals(wallet.getWalletName(), walletDto.getWalletName());
    }

    @Test
    void testGetAllWalletForCustomer(){
        WalletListRequest walletListRequest = new WalletListRequest();
        walletListRequest.setAmount(BigDecimal.TEN);
        walletListRequest.setCustomerId(1L);
        walletListRequest.setCurrency("EUR");
        walletService.getAllWalletForCustomer(walletListRequest);
    }
}