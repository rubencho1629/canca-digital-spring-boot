package com.banca.digital.services;

import com.banca.digital.entities.BankAccount;
import com.banca.digital.entities.CurrentAccount;
import com.banca.digital.entities.SavingsAccount;
import com.banca.digital.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void  consult() {
        BankAccount bankAccountBBDD = bankAccountRepository.findById("5a58daf3-68e5-415b-bc98-debe8ecc4288").orElse(null);

        if (bankAccountBBDD != null) {
            System.out.println("Bank Account: " + bankAccountBBDD.getId());
            System.out.println("Balance: " + bankAccountBBDD.getBalance());
            System.out.println("Creation Date: " + bankAccountBBDD.getCreationDate());
            System.out.println("Status: " + bankAccountBBDD.getStatus());
            System.out.println("Client: " + bankAccountBBDD.getClient().getName());
            System.out.println("Operations: " + bankAccountBBDD.getAccountOperations().size());


            if (bankAccountBBDD instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccountBBDD;
                System.out.println("Overdraft: " + currentAccount.getOverdraft());
            }
            else if (bankAccountBBDD instanceof SavingsAccount) {
                SavingsAccount savingsAccount = (SavingsAccount) bankAccountBBDD;
                System.out.println("Interest Rate: " + savingsAccount.getInterestRate());
            }
            bankAccountBBDD.getAccountOperations().forEach(accountOperation -> {
                System.out.println("Operation: " + accountOperation.getId());
                System.out.println("Amount: " + accountOperation.getAmount());
                System.out.println("Creation Date: " + accountOperation.getCreationDate());
                System.out.println("Operation Type: " + accountOperation.getOperationType());
            });
        }
    }
}
