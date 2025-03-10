package com.digital.wallet.wallet_service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends BaseModel{
    private String name;
    private String surname;
    @Column(unique = true)
    private String tckn;
    private String password;
    private String roles;
}
