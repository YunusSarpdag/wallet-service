package com.digital.wallet.wallet_service.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

//This class is used to configure web security.
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurtityConfig{

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorizedRequests ->
                        authorizedRequests
                                .requestMatchers("/api/v1/transaction/deposit").hasAnyRole("CUSTOMER","EMPLOYEE")
                                .requestMatchers("/api/v1/transaction/listTransactionForWallet").hasAnyRole("CUSTOMER","EMPLOYEE")
                                .requestMatchers("/api/v1/transaction/withDraw").hasAnyRole("CUSTOMER","EMPLOYEE")
                                .requestMatchers("/api/v1/transaction/approve").hasAnyRole("EMPLOYEE")
                                .requestMatchers("/api/v1/wallet/creteWallet").hasAnyRole("CUSTOMER","EMPLOYEE")
                                .requestMatchers("/api/v1/wallet/listWallet").hasAnyRole("CUSTOMER","EMPLOYEE")
                                .requestMatchers("/api/v1/wallet/listWallet").hasAnyRole("CUSTOMER","EMPLOYEE")
                                .requestMatchers("/h2-console/**").permitAll()
                                .anyRequest().authenticated()
                ).httpBasic(withDefaults())
                .formLogin(withDefaults())
                .csrf().disable()
                .headers().frameOptions().disable();
        return http.build();
    }
}
