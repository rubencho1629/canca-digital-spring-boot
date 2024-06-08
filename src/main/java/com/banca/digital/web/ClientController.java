package com.banca.digital.web;

import com.banca.digital.dtos.ClientDTO;
import com.banca.digital.entities.Client;
import com.banca.digital.exceptions.ClientNotFoundException;
import com.banca.digital.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return bankAccountService.getClients();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable(name = "id") Long clientId) throws ClientNotFoundException {
        return bankAccountService.getClient(clientId);

    }

    @PostMapping("/clients")
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO) {
        return bankAccountService.saveClient(clientDTO);

    }

    @PutMapping("/clients/{id}")
    public ClientDTO updateClient(@PathVariable(name = "id") Long clientId, @RequestBody ClientDTO clientDTO) throws ClientNotFoundException {
        clientDTO.setId(clientId);
        return bankAccountService.updateClient(clientDTO);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable(name = "id") Long clientId) throws ClientNotFoundException {
        bankAccountService.deleteClient(clientId);
    }
}
