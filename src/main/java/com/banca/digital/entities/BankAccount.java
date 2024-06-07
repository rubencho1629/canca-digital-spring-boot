package com.banca.digital.entities;

import com.banca.digital.enums.StatusAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 4)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    @Id
    private String id;

    private double balance;

    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private StatusAccount status;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;
}
