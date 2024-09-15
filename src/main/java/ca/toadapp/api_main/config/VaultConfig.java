package ca.toadapp.api_main.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("db")
public class VaultConfig {
	private String driver;
	private String url;
	private String user;
	private String password;
}
