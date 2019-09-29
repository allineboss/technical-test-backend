package com.playtomic.tests.wallet;

import com.playtomic.tests.wallet.model.ClientModel;
import com.playtomic.tests.wallet.model.MovementModel;
import com.playtomic.tests.wallet.model.WalletModel;
import com.playtomic.tests.wallet.repository.IClientRepository;
import com.playtomic.tests.wallet.repository.IMovementRepository;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;

@SpringBootApplication
@ComponentScan(basePackages = {"com.playtomic.tests"})
public class WalletApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(
			IClientRepository clientRepository, IWalletRepository walletRepository, IMovementRepository movementRepository) {
		return (args) -> {
			ClientModel clientModel = new ClientModel("Alline Machado Boss", "allineboss@gmail.com");
			clientRepository.save(clientModel);

			WalletModel wallet = new WalletModel(clientModel);
			walletRepository.save(wallet);

			BigDecimal amount = BigDecimal.valueOf(300);
			MovementModel movement = new MovementModel(amount, wallet);
			movementRepository.save(movement);
		};
	}
}
