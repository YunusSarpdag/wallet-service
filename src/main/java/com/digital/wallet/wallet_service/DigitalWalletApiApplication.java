package com.digital.wallet.wallet_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.digital.wallet.wallet_service.model.entity")
public class DigitalWalletApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalWalletApiApplication.class, args);
	}

}
