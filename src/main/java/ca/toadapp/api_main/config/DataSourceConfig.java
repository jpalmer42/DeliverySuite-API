package ca.toadapp.api_main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.java.Log;

@Log
@Configuration
public class DataSourceConfig {

	@Autowired
	private VaultConfig vaultConfig;

	@Bean
	public DataSource getDataSource() {
		final var driver = vaultConfig.getDriver();
		final var url = vaultConfig.getUrl();
		final var user = vaultConfig.getUser();
		final var pwd = vaultConfig.getPassword();

		log.info( String.format( "dbURL: %s, dbUser: %s", url, user ) );
		 
		final var dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName( driver );
		dataSourceBuilder.url( url );
		dataSourceBuilder.username( user );
		dataSourceBuilder.password( pwd );

		return dataSourceBuilder.build();
	}
}
