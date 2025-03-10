package com.digital.wallet.wallet_service.model.service;

import com.digital.wallet.wallet_service.model.entity.Wallet;
import com.digital.wallet.wallet_service.model.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IbanGeneratorTest {

    @InjectMocks
    private IbanGenerator ibanGenerator;

    @Mock
    private WalletService walletService;

    @Test
    void testGenerateUniqueIban() {
        Set<String> ibanSet = new HashSet<>();
        for(int i = 0 ; i< 1000; i++){
            when(walletService.getWalletByIban(anyString())).thenReturn(null);
            String iban = ibanGenerator.generateUniqueIban();
            ibanSet.add(iban);
        }
        assertEquals(1000, ibanSet.size());
    }
}