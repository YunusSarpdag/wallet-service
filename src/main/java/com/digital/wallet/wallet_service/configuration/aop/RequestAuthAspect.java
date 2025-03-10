package com.digital.wallet.wallet_service.configuration.aop;

import com.digital.wallet.wallet_service.dto.PaymentRequest;
import com.digital.wallet.wallet_service.dto.WalletListRequest;
import com.digital.wallet.wallet_service.model.entity.Customer;
import com.digital.wallet.wallet_service.model.entity.Wallet;
import com.digital.wallet.wallet_service.model.service.CustomerService;
import com.digital.wallet.wallet_service.model.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class RequestAuthAspect {
    private final CustomerService customerService;
    private final WalletService walletService;
    @Before("execution(* com.digital.wallet.wallet_service.model.service.WalletService.getAllWalletForCustomer(..))")
    public void checkRequestGetAllWalletForCustomer(JoinPoint joinPoint) {
        Object [] args = joinPoint.getArgs();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.getCustomerByTckn(authentication.getName());
        WalletListRequest request = (WalletListRequest) args[0];
        if(customer.getRoles().equals("CUSTOMER") && request.getCustomerId()!=customer.getId()){
            throw new RuntimeException("asfasfasf");
        }
    }

    @Before("execution(* com.digital.wallet.wallet_service.model.service.TransactionService.getAllTransactionForWallet(..))")
    public void checkRequestGetAllTransactionForWallet(JoinPoint joinPoint) {
        Object [] args = joinPoint.getArgs();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.getCustomerByTckn(authentication.getName());
        Long walletId = (Long) args[0];
        if(customer.getRoles().equals("CUSTOMER") && customer.getId()!= walletId){
            throw new RuntimeException("You only authorized for yourself data");
        }
    }

    @Before("execution(* com.digital.wallet.wallet_service.model.service.TransactionService.withDraw(..)) || execution(* com.digital.wallet.wallet_service.model.service.TransactionService.deposit(..))")
    public void checkRequestWithDrawAndDeposit(JoinPoint joinPoint) {
        Object [] args = joinPoint.getArgs();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.getCustomerByTckn(authentication.getName());
        PaymentRequest paymentRequest = (PaymentRequest) args[0];
        Wallet wallet = walletService.getWalletById(paymentRequest.getWalletId());
        if(customer.getRoles().equals("CUSTOMER") && customer.getId()!= wallet.getCustomerId()){
            throw new RuntimeException("You only authorized for yourself data");
        }
    }
}
