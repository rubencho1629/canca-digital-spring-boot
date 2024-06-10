package com.banca.digital;

import com.banca.digital.dtos.BankAccountDTO;
import com.banca.digital.dtos.ClientDTO;
import com.banca.digital.dtos.CurrentAccountDTO;
import com.banca.digital.dtos.SavingsAccountDTO;
import com.banca.digital.exceptions.BankAccountNotFoundExcetion;
import com.banca.digital.exceptions.ClientNotFoundException;
import com.banca.digital.exceptions.InsufficientBalanceException;
import com.banca.digital.services.BankAccountService;
import com.banca.digital.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class ApiBancaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBancaDigitalApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(BankService bankService) {
		return args -> {
			bankService.consult();
		};
	}

	@Bean
	CommandLineRunner start(BankAccountService bankAccountService) {
		return args -> {
			Stream.of("Client 1", "Client 2", "Client 3").forEach(c -> {
				ClientDTO client = new ClientDTO();
				client.setName(c);
				client.setEmail(c.replaceAll(" ", "").toLowerCase() + "@gmail.com");
				bankAccountService.saveClient(client);
			});

			bankAccountService.getClients().forEach(client1 -> {
				try {
					bankAccountService.saveCurrentAccount(1000, 100, client1.getId());
					bankAccountService.saveSavingsAccount(1000, 0.1, client1.getId());

					List<BankAccountDTO> bankAccounts = bankAccountService.getBankAccount();

					for (BankAccountDTO bankAccountDTO : bankAccounts) {
						for (int i = 0; i < 10; i++) {
							String accountId;
							if (bankAccountDTO instanceof CurrentAccountDTO) {
								accountId = ((CurrentAccountDTO) bankAccountDTO).getId();
							} else {
								accountId = ((SavingsAccountDTO) bankAccountDTO).getId();
							}

							bankAccountService.credit(accountId, 100*Math.random()*15555, "Credit");
							bankAccountService.debit(accountId, 50*Math.random()*4854855, "Debit");
						}
					}
				} catch (ClientNotFoundException | BankAccountNotFoundExcetion | InsufficientBalanceException e) {
					e.printStackTrace();
				}
			});
		};
	}
}
