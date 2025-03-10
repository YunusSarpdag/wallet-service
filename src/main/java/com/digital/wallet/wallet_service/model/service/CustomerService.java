package com.digital.wallet.wallet_service.model.service;

import com.digital.wallet.wallet_service.model.entity.Customer;
import com.digital.wallet.wallet_service.model.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String tckn) {
        Customer userEntity = customerRepository.findByTckn(tckn);

        if(userEntity==null){
            new RuntimeException("User not found");
        }

        return User.builder()
                .username(userEntity.getTckn())
                .password(userEntity.getPassword())
                .roles(userEntity.getRoles())
                .build();
    }

    /**
     * Retrieves the customer with the given ID from the database.
     * @param id
     * @return
     */
    public Customer getCustomerById(Long id){
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()){
            return customerOptional.get();
        }else {
            throw new RuntimeException("Customer Not Found");
        }
    }

    public Customer getCustomerByTckn(String tckn){
        return customerRepository.findByTckn(tckn);
    }
}
