package com.digital.wallet.wallet_service.model.service;

import com.digital.wallet.wallet_service.model.entity.Wallet;
import com.digital.wallet.wallet_service.model.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class IbanGenerator {
    private final WalletRepository walletRepository;
    private static final String COUNTRY_CODE = "TR";
    private static final int IBAN_LENGTH = 26;
    private static final Random random = new Random();

    /**
     * Checks created iban is unique
     * @param iban
     * @return
     */
    private boolean isIbanUnique(String iban) {
        Wallet wallet = walletRepository.findByIban(iban);
        return wallet == null;
    }

    /**
     * Creates a unique IBAN (International Bank Account Number).
     * @return
     */
    public String generateUniqueIban() {
        String iban;
        do {
            iban = generateRandomIban();
        } while (!isIbanUnique(iban));
        return iban;
    }

    /**
     * Generates a random IBAN (International Bank Account Number) number.
     * @return
     */
    private String generateRandomIban() {
        StringBuilder ibanBuilder = new StringBuilder(COUNTRY_CODE);
        ibanBuilder.append(String.format("%02d", random.nextInt(99)));

        while (ibanBuilder.length() < IBAN_LENGTH) {
            ibanBuilder.append(String.format("%04d", random.nextInt(10000)));
        }

        return ibanBuilder.substring(0, IBAN_LENGTH);
    }
}
