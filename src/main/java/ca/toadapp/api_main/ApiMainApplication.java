package ca.toadapp.api_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "ca.toadapp")
@EntityScan(basePackages = "ca.toadapp")
@EnableJpaRepositories(basePackages = "ca.toadapp")
@SpringBootApplication
public class ApiMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMainApplication.class, args);
	}

}
