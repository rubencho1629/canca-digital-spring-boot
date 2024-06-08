package com.banca.digital.services.impl;


import com.banca.digital.dtos.ClientDTO;
import com.banca.digital.entities.*;
import com.banca.digital.enums.OperationType;
import com.banca.digital.enums.StatusAccount;
import com.banca.digital.exceptions.BankAccountNotFoundExcetion;
import com.banca.digital.exceptions.ClientNotFoundException;
import com.banca.digital.exceptions.InsufficientBalanceException;
import com.banca.digital.mappers.bankAccountMapperImpl;
import com.banca.digital.repositories.AccountOperationRepository;
import com.banca.digital.repositories.BankAccountRepository;
import com.banca.digital.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BankAccountService implements com.banca.digital.services.BankAccountService {


    @Autowired
    private ClientRepository clientRepository;


    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Autowired
    private AccountOperationRepository accountOperationRepository;

    @Autowired
    private bankAccountMapperImpl bankAccountMapper;

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        log.info("Saving client: {}", clientDTO);
        Client client = bankAccountMapper.mapToClient(clientDTO); // Cambia el método al nombre correcto
        Client clientBBDD = clientRepository.save(client);
        return bankAccountMapper.mapToClientDTO(clientBBDD);
    }

    @Override
    public ClientDTO getClient(Long id) throws ClientNotFoundException {
        Client client = clientRepository.findById(id).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }
        ClientDTO clientDTO = bankAccountMapper.mapToClientDTO(client);
        return clientDTO;
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) throws ClientNotFoundException {
        log.info("Updating client: {}", clientDTO);

        // Verifica si el cliente existe antes de intentar actualizar
        if (!clientRepository.existsById(clientDTO.getId())) {
            throw new ClientNotFoundException("Client not found with ID: " + clientDTO.getId());
        }

        // Mapea de ClientDTO a Client
        Client client = bankAccountMapper.mapToClient(clientDTO);

        // Guarda el cliente actualizado en la base de datos
        Client clientBBDD = clientRepository.save(client);

        // Mapea de Client a ClientDTO y retorna
        return bankAccountMapper.mapToClientDTO(clientBBDD);
    }


    @Override
    public void deleteClient(Long id) throws ClientNotFoundException {
        clientRepository.deleteById(id);
    }

    @Override
    public CurrentAccount saveCurrentAccount(double balance, double overdraft, Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreationDate(new Date());
        currentAccount.setBalance(balance);
        currentAccount.setStatus(StatusAccount.CREATED);
        currentAccount.setClient(client);
        currentAccount.setOverdraft(overdraft);

        CurrentAccount currentAccountBBDD = bankAccountRepository.save(currentAccount);
        return currentAccountBBDD;

    }


    @Override
    public SavingsAccount saveSavingsAccount(double balance, double interestRate, Long clientId) throws ClientNotFoundException {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException("Client not found");
        }
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId(UUID.randomUUID().toString());
        savingsAccount.setCreationDate(new Date());
        savingsAccount.setBalance(balance);
        savingsAccount.setStatus(StatusAccount.CREATED);
        savingsAccount.setClient(client);
        savingsAccount.setInterestRate(interestRate);

        SavingsAccount savingsAccountBBDD = bankAccountRepository.save(savingsAccount);
        return savingsAccountBBDD;
    }

    @Override
    public List<ClientDTO> getClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOS = clients.stream().map(client -> bankAccountMapper.mapToClientDTO(client)).collect(Collectors.toList());
        return clientDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundExcetion {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundExcetion("Bank Account not found"));
        return bankAccount;
    }


    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundExcetion, InsufficientBalanceException {
        BankAccount bankAccount = getBankAccount(accountId);
        if (bankAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setCreationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);

        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundExcetion {
        BankAccount bankAccount = getBankAccount(accountId);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setCreationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);

        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);


    }

    @Override
    public void transfer(String originAccountId, String targetAccountId, double amount) throws BankAccountNotFoundExcetion, InsufficientBalanceException {
        debit(originAccountId, amount, "Transfer to " + targetAccountId);
        credit(targetAccountId, amount, "Transfer from " + originAccountId);

    }

    @Override
    public List<BankAccount> getBankAccount() {
        return bankAccountRepository.findAll();
    }
}
