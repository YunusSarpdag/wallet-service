package com.digital.wallet.wallet_service.configuration;

import com.digital.wallet.wallet_service.model.entity.Customer;
import com.digital.wallet.wallet_service.model.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Class for initialize dummy customer and employee
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.count() == 0) {
            Customer customer1 = new Customer();
            customer1.setName("John");
            customer1.setRoles("CUSTOMER");
            customer1.setTckn("123456789");
            customer1.setPassword(passwordEncoder.encode("password"));
            customer1.setSurname("Doe");
            customerRepository.save(customer1);
            Customer customer2 = new Customer();
            customer2.setName("Alice");
            customer2.setRoles("CUSTOMER");
            customer2.setTckn("123456780");
            customer2.setSurname("Walt");
            customer2.setPassword(passwordEncoder.encode("password"));
            customerRepository.save(customer2);
            Customer customer3 = new Customer();
            customer3.setName("Marry");
            customer3.setRoles("CUSTOMER");
            customer3.setTckn("123456782");
            customer3.setSurname("Marry");
            customer3.setPassword(passwordEncoder.encode("password"));
            customerRepository.save(customer3);
            Customer customer4 = new Customer();
            customer4.setName("Admin");
            customer4.setRoles("EMPLOYEE");
            customer4.setTckn("123456781");
            customer4.setSurname("Admin");
            customer4.setPassword(passwordEncoder.encode("admin_password"));
            customerRepository.save(customer4);

        }
    }
}