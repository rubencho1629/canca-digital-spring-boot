package com.banca.digital.dtos;

import com.banca.digital.entities.Client;
import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String email;


}
