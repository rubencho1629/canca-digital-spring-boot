package com.banca.digital;

import com.banca.digital.entities.*;
import com.banca.digital.enums.OperationType;
import com.banca.digital.enums.StatusAccount;
import com.banca.digital.exceptions.BankAccountNotFoundExcetion;
import com.banca.digital.exceptions.ClientNotFoundException;
import com.banca.digital.exceptions.InsufficientBalanceException;
import com.banca.digital.repositories.AccountOperationRepository;
import com.banca.digital.repositories.BankAccountRepository;
import com.banca.digital.repositories.ClientRepository;
import com.banca.digital.services.BankAccountService;
import com.banca.digital.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
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




	//@Bean
	CommandLineRunner start(BankAccountService bankAccountService) {
		return args -> {
			Stream.of("Client 1", "Client 2", "Client 3").forEach(c -> {
				Client client = new Client();
				client.setName(c);
				client.setEmail(c.replaceAll(" ", "").toLowerCase() + "@gmail.com");
				bankAccountService.saveClient(client);
			});

				bankAccountService.getClients().forEach(client1 -> {
					try {
						bankAccountService.saveCurrentAccount(1000, 100, client1.getId());
						bankAccountService.saveSavingsAccount(1000, 0.1, client1.getId());

						List<BankAccount> bankAccounts = bankAccountService.getBankAccount();

						for (int i = 0; i < 10; i++) {
							bankAccountService.debit(bankAccounts.get(0).getId(), 100 + Math.random() * 12000, "Debit operation");
							bankAccountService.credit(bankAccounts.get(1).getId(), 100 + Math.random() * 10000, "Credit operation");
							bankAccountService.transfer(bankAccounts.get(0).getId(), bankAccounts.get(1).getId(), 100);
						}
					} catch (ClientNotFoundException e) {
						e.printStackTrace();
					} catch (BankAccountNotFoundExcetion e) {
                        throw new RuntimeException(e);
                    } catch (InsufficientBalanceException e) {
                        throw new RuntimeException(e);
                    }
                });




			};
		}
	}