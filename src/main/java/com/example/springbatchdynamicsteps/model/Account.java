package com.example.springbatchdynamicsteps.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class Account {

    @Id
    private String accountNumber;
    private BigDecimal balance;

}
