package ca.toadapp.api_main.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configureContentNegotiation( ContentNegotiationConfigurer configurer ) {
		configurer.defaultContentType( MediaType.APPLICATION_JSON );
	}

	@Override
	public void addCorsMappings( CorsRegistry registry ) {
		registry.addMapping( "/**" ).allowedMethods( "*" );
	}
}
