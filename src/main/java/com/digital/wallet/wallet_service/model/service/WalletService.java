package com.digital.wallet.wallet_service.model.service;

import com.digital.wallet.wallet_service.dto.WalletDto;
import com.digital.wallet.wallet_service.dto.WalletListRequest;
import com.digital.wallet.wallet_service.model.entity.Customer;
import com.digital.wallet.wallet_service.model.entity.Wallet;
import com.digital.wallet.wallet_service.model.entity.enums.Currency;
import com.digital.wallet.wallet_service.model.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final CustomerService customerService;
    private final IbanGenerator ibanGenerator;

    /**
     * This method creates a wallet based on the `WalletDto` object received from the user.
     * The currency of the wallet must be a valid currency (USD, EUR, TRY).
     * Also, other necessary information for the wallet is set and saved in the wallet database.
     * @param walletDto
     * @return
     */
    public Wallet createWallet(WalletDto walletDto){
        if(!Currency.contains(walletDto.getCurrency())){
            throw new RuntimeException("Currency must be USD or EUR or TRY");
        }

        Customer customer = customerService.getCustomerById(walletDto.getCustomerId());

        Wallet wallet = new Wallet();
        wallet.setWalletName(walletDto.getWalletName());
        wallet.setCurrency(Currency.valueOf(walletDto.getCurrency()));
        wallet.setActiveForShopping(walletDto.getActiveForShopping());
        wallet.setActiveForWithdraw(walletDto.getActiveForWithdraw());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUsableBalance(BigDecimal.ZERO);
        wallet.setCustomerId(customer.getId());
        wallet.setIban(ibanGenerator.generateUniqueIban());
        wallet.setCreatedOn(Instant.now());
        wallet.setLastUpdatedOn(Instant.now());
        wallet = walletRepository.save(wallet);
        return wallet;
    }


    /**
     * This method returns wallets for the given customer ID. If desired, filters can be applied, such as wallets being a certain currency
     * or having a certain balance.
     * @param request
     * @return
     */
    public List<Wallet> getAllWalletForCustomer(WalletListRequest request){
        List<Wallet> walletList = walletRepository.findByCustomerId(request.getCustomerId());
        if(request.getCurrency()!= null){
            if(!Currency.contains(request.getCurrency())){
                throw new RuntimeException("Currency must be USD or EUR or TRY or null");
            }
            walletList = walletList.stream().filter(wallet -> wallet.getCurrency().equals(request.getCurrency())).collect(Collectors.toList());
        }
        if(request.getAmount()!= null && request.getAmount().compareTo(BigDecimal.ZERO)>0){
            walletList = walletList.stream().filter(wallet -> wallet.getBalance().compareTo(request.getAmount())>0).collect(Collectors.toList());
        }

        return walletList;
    }

    /**
     * This method queries the database for the wallet with the given `walletId`. If the wallet exists,
     * the wallet object is returned. If the wallet is not found, an error is thrown.
     * @param walletId
     * @return
     */
    public Wallet getWalletById(Long walletId){
        Optional<Wallet> optionalWallet = walletRepository.findById(walletId);
        if(optionalWallet.isPresent()){
            return optionalWallet.get();
        }else{
            throw new RuntimeException("Wallet not found");
        }
    }

    /**
     * This method queries the database for a wallet with a specific IBAN number. If the wallet exists,
     * the wallet object is returned. If the wallet is not found, an error is thrown.
     * @param iban
     * @return
     */
    public Wallet getWalletByIban(String iban){
        Wallet wallet = walletRepository.findByIban(iban);
        if(wallet != null){
            return wallet;
        }else{
            throw new RuntimeException("Wallet not found");
        }
    }

    /**
     * This method saves the given `Wallet` object to the database. If the wallet exists in the database,
     *  the existing wallet data is updated. If the wallet does not exist in the database, it is saved as a new wallet.
     * @param wallet
     */
    public void updateWallet(Wallet wallet){
        walletRepository.save(wallet);
    }
}
