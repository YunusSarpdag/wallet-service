package com.digital.wallet.wallet_service.model.service;

import com.digital.wallet.wallet_service.model.entity.Customer;
import com.digital.wallet.wallet_service.model.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void testGetCustomerByIdForExistingCustomer() {
        when(customerRepository.findById(any())).thenReturn(Optional.of(new Customer()));
        Customer customer = customerService.getCustomerById(2L);
        assertNotNull(customer);
    }

    @Test
    void testGetCustomerByIdForNonExistingCustomer() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerById(2L);
        });
        String expectedMessage = "Customer Not Found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}