package com.banca.digital.mappers;


import com.banca.digital.dtos.ClientDTO;
import com.banca.digital.dtos.CurrentAccountDTO;
import com.banca.digital.dtos.SavingsAccountDTO;
import com.banca.digital.entities.Client;
import com.banca.digital.entities.CurrentAccount;
import com.banca.digital.entities.SavingsAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class bankAccountMapperImpl {

    public ClientDTO mapToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client, clientDTO);
        return clientDTO;
    }

    public Client mapToClient(ClientDTO clientDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        return client;
    }

    public SavingsAccountDTO mapToSavingsAccountDTO(SavingsAccount savingsAccount) {
        SavingsAccountDTO savingsAccountDTO = new SavingsAccountDTO();
        BeanUtils.copyProperties(savingsAccount, savingsAccountDTO);
        savingsAccountDTO.setClientDTO(mapToClientDTO(savingsAccount.getClient()));
        return savingsAccountDTO;
    }
    public SavingsAccount mapToSavingsAccount(SavingsAccountDTO savingsAccountDTO) {
        SavingsAccount savingsAccount = new SavingsAccount();
        BeanUtils.copyProperties(savingsAccountDTO, savingsAccount);
        savingsAccount.setClient(mapToClient(savingsAccountDTO.getClientDTO()));
        return savingsAccount;
    }
    public CurrentAccountDTO mapToCurrentAccountDTO(CurrentAccount currentAccount) {
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentAccountDTO);
        currentAccountDTO.setClientDTO(mapToClientDTO(currentAccount.getClient()));
        return currentAccountDTO;
    }
    public CurrentAccount mapToCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDTO, currentAccount);
        currentAccount.setClient(mapToClient(currentAccountDTO.getClientDTO()));
        return currentAccount;
    }
}