package com.banca.digital.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("CA")
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccount extends BankAccount {

        private double overdraft;
}
