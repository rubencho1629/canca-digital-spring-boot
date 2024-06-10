package com.banca.digital.web;

import com.banca.digital.dtos.BankAccountDTO;
import com.banca.digital.exceptions.BankAccountNotFoundExcetion;
import com.banca.digital.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/v1")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/bank-accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable String id) throws BankAccountNotFoundExcetion {
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping("/bank-accounts")
    public List<BankAccountDTO> getBankAccount() {
        return bankAccountService.getBankAccount();
    }
}
