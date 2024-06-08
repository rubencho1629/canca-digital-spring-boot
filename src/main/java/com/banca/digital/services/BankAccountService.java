package com.banca.digital.services;

import com.banca.digital.dtos.ClientDTO;
import com.banca.digital.entities.BankAccount;
import com.banca.digital.entities.CurrentAccount;
import com.banca.digital.entities.SavingsAccount;
import com.banca.digital.exceptions.BankAccountNotFoundExcetion;
import com.banca.digital.exceptions.ClientNotFoundException;
import com.banca.digital.exceptions.InsufficientBalanceException;

import java.util.List;

public interface BankAccountService {

    ClientDTO saveClient(ClientDTO clientDTO);



    ClientDTO getClient(Long id) throws ClientNotFoundException;

    ClientDTO updateClient( ClientDTO clientDTO) throws ClientNotFoundException;

    void deleteClient(Long id) throws ClientNotFoundException;

    CurrentAccount saveCurrentAccount(double balance, double overdraft, Long clientId) throws ClientNotFoundException;

    SavingsAccount saveSavingsAccount(double balance, double interestRate, Long clientId) throws ClientNotFoundException;

    List<ClientDTO> getClients();

    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundExcetion;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundExcetion, InsufficientBalanceException;

    void credit(String accountId, double amount, String description) throws BankAccountNotFoundExcetion;

    void transfer(String originAccountId, String targetAccountId, double amount) throws BankAccountNotFoundExcetion, InsufficientBalanceException;

    List<BankAccount> getBankAccount() ;
}
