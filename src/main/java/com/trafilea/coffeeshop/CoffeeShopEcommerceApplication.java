package com.trafilea.coffeeshop;

import com.trafilea.coffeeshop.model.ERole;
import com.trafilea.coffeeshop.model.RoleEntity;
import com.trafilea.coffeeshop.model.UserEntity;
import com.trafilea.coffeeshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class CoffeeShopEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeShopEcommerceApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Bean
	CommandLineRunner init(){
		return args -> {
			UserEntity user1 = new UserEntity(
					"lprodan",
					"lprodan@gmail.com",
					passwordEncoder.encode("1234"),
					Set.of(new RoleEntity(ERole.valueOf(ERole.ADMIN.name()))));

			UserEntity user2 = new UserEntity(
					"pablonap",
					"pablonap@gmail.com",
					passwordEncoder.encode("1234"),
					Set.of(new RoleEntity(ERole.valueOf(ERole.USER.name()))));

			userRepository.save(user1);
			userRepository.save(user2);
		};
	}

}
