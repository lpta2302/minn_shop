package dev.minn_shop.minn_shop;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import dev.minn_shop.minn_shop.product.ProductRepository;
import dev.minn_shop.minn_shop.product.ProductService;
import dev.minn_shop.minn_shop.product.size.SizeService;
import dev.minn_shop.minn_shop.user.role.Role;
import dev.minn_shop.minn_shop.user.role.RoleRepository;
import dev.minn_shop.minn_shop.user.role.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaAuditing
@RequiredArgsConstructor
@Slf4j
public class MinnShopApplication {
	private final SizeService sizeService;
	private final RoleRepository roleRepository;
	private final ProductService productService;
	private final ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(MinnShopApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			roleRepository.save(Role.builder().name(RoleType.CUSTOMER).build());

			sizeService.addSizesByValues(List.of(
				"US 40",
				"US 40.5",
				"US 41"
			));
			// ObjectMapper objectMapper = new ObjectMapper();
			// TypeReference<List<DetailProductRecord>> typeReference = new
			// TypeReference<List<DetailProductRecord>>() {
			// };
			// InputStream inputStream =
			// TypeReference.class.getResourceAsStream("/data/products.json");

			// List<DetailProductRecord> productRecords =
			// objectMapper.readValue(inputStream, typeReference);
			// productRecords.forEach(record->productService.addProduct(record));
			};	
	}
}
