package ca.toadapp.api_main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.java.Log;

@Log
@Configuration
public class DataSourceConfig {
	@Value("${db.driver}")
	private String driver;
	
	@Value("${db.url}")
	private String url;
	
	@Value("${db.user}")
	private String user;
	
	@Value("${db.password}")
	private String password;

	@Bean
	public DataSource getDataSource() {
		
		log.info( String.format( "dbURL: %s, dbUser: %s", url, user ) );
		 
		final var dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName( driver );
		dataSourceBuilder.url( url );
		dataSourceBuilder.username( user );
		dataSourceBuilder.password( password );

		return dataSourceBuilder.build();
	}
}
