package ca.toadapp.api_main.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ca.toadapp.api_main.security.service.ServiceFirebase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

@Log
@Component
public class FilterAuthFirebase extends OncePerRequestFilter {

	@Autowired
	ServiceFirebase firebaseService;

	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {

		// If a previous filter processed, then skip this filter
		if( SecurityContextHolder.getContext().getAuthentication() == null ) {
			log.info( request.getMethod() );
			log.info( request.getRequestURI() );

			try {
				String authHeader = request.getHeader( "Authorization" );

				if( authHeader != null && authHeader.startsWith( "Bearer " ) ) {
					final String token = authHeader.substring( 7 );
					var authToken = new AppAuthenticationToken( token, firebaseService );
					SecurityContextHolder.getContext().setAuthentication( authToken );
				}

			}
			catch ( Exception ex ) {
				throw new ServletException( ex.getMessage() );
			}
		}

		filterChain.doFilter( request, response );
	}

}
