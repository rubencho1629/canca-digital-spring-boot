package com.banca.digital.mappers;


import com.banca.digital.dtos.ClientDTO;
import com.banca.digital.entities.Client;
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
}
