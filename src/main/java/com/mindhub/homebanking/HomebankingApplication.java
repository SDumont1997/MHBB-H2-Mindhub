package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return args -> 	{
			Loan mortgage = new Loan("Mortgage", 500000.00, new ArrayList<>(){{add(12); add(24); add(36); add(48); add(60);}});
			loanRepository.save(mortgage);
			Loan personal = new Loan("Personal", 100000.00, new ArrayList<>(){{add(6); add(12); add(24);}});
			loanRepository.save(personal);
			Loan vehicle = new Loan("Vehicle", 300000.00, new ArrayList<>(){{add(6); add(12); add(24); add(36);}});
			loanRepository.save(vehicle);

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba1234"));
			clientRepository.save(client1);
			Account account1 = new Account(client1, "MHB-1", 5000.00);
			accountRepository.save(account1);
			Transaction transaction1 = new Transaction(account1, TransactionType.DEBIT, 5000.00, "MHB003 - Santiago Dumont");
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(account1,TransactionType.CREDIT, 2700.00, "MHB004 - Santiago Dumont");
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction(account1, TransactionType.CREDIT, 5000.00, "MHB003 - Santiago Dumont");
			transactionRepository.save(transaction3);
			Account account2 = new Account(client1, "MHB-2", 7500.00);
			accountRepository.save(account2);
			Transaction transaction4 = new Transaction(account2, TransactionType.CREDIT, 3200.00, "MHB004 - Santiago Dumont");
			transactionRepository.save(transaction4);
			Transaction transaction5 = new Transaction(account2, TransactionType.DEBIT, 4000.00, "MHB003 - Santiago Dumont");
			transactionRepository.save(transaction5);
			ClientLoan clientLoan1 = new ClientLoan(400000.00, 60, client1, mortgage);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.00, 12, client1, personal);
			clientLoanRepository.save(clientLoan2);
			Card card1 = new Card(client1, CardType.DEBIT, CardColor.GOLD, "4563 8000 7465 9784", 772, LocalDate.now(), LocalDate.now().plusYears(5));
			cardRepository.save(card1);
			Card card2 = new Card(client1, CardType.CREDIT, CardColor.TITANIUM, "7705 7834 5500 3214", 238, LocalDate.now(), LocalDate.now().plusYears(5));
			cardRepository.save(card2);

			Client client2 = new Client("Santiago", "Dumont", "sdumont@gmail.com", passwordEncoder.encode("santiago1234"));
			clientRepository.save(client2);
			Account account3 = new Account(client2, "MHB-3", 8000.00);
			accountRepository.save(account3);
			Transaction transaction6 = new Transaction(account3, TransactionType.CREDIT, 5000.00, "MHB001 - Melba Morel");
			transactionRepository.save(transaction6);
			Transaction transaction7 = new Transaction(account3, TransactionType.DEBIT, 5000.00, "MHB001 - Melba Morel");
			transactionRepository.save(transaction7);
			Transaction transaction8 = new Transaction(account3, TransactionType.CREDIT, 4000.00, "MHB002 - Melba Morel");
			transactionRepository.save(transaction8);
			Account account4 = new Account(client2, "MHB-4", 10000.00);
			accountRepository.save(account4);
			Transaction transaction9 = new Transaction(account4, TransactionType.DEBIT, 2700.00, "MHB001 - Melba Morel");
			transactionRepository.save(transaction9);
			Transaction transaction10 = new Transaction(account4, TransactionType.DEBIT, 3200.00, "MHB002 - Melba Morel");
			transactionRepository.save(transaction10);
			ClientLoan clientLoan3 = new ClientLoan(100000.00, 24, client2, personal);
			clientLoanRepository.save(clientLoan3);
			ClientLoan clientLoan4 = new ClientLoan(200000.00, 36, client2, vehicle);
			clientLoanRepository.save(clientLoan4);
			Card card3 = new Card(client2, CardType.CREDIT, CardColor.SILVER, "5450 8842 8080 9908", 725, LocalDate.now(), LocalDate.now().plusYears(5));
			cardRepository.save(card3);
			Card card4 = new Card(client2, CardType.DEBIT, CardColor.TITANIUM, "6080 2253 9846 3300", 226, LocalDate.now().minusYears(2), LocalDate.now().plusYears(3));
			cardRepository.save(card4);
		};
	}
}
