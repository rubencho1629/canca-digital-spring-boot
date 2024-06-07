package com.banca.digital.web;

import com.banca.digital.entities.Client;
import com.banca.digital.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/clients")
    public List<Client> getClients() {
        return bankAccountService.getClients();
    }
}
