package dev.minn_shop.minn_shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import dev.minn_shop.minn_shop.user.Role;
import dev.minn_shop.minn_shop.user.RoleRepository;
import dev.minn_shop.minn_shop.user.RoleType;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaAuditing
@RequiredArgsConstructor
public class MinnShopApplication {
	private final RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(MinnShopApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(){
		return args -> {roleRepository.save(Role.builder().name(RoleType.CUSTOMER).build());};
	}

}
