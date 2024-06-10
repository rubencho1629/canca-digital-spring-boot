package com.banca.digital.dtos;

import com.banca.digital.enums.StatusAccount;
import lombok.Data;

import java.util.Date;

@Data
public class SavingsAccountDTO extends BankAccountDTO {

    private String id;
    private double balance;
    private Date creationDate;
    private StatusAccount status;
    private ClientDTO clientDTO;
    private double interestRate;

}
