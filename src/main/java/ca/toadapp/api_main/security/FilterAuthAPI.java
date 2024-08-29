package ca.toadapp.api_main.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

@Log
@Component
public class FilterAuthAPI extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// If a previous filter processed, then skip this filter
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			log.info(request.getMethod());
			log.info(request.getRequestURI());

			try {
				String authHeader = request.getHeader("Authorization");

				if (authHeader != null && authHeader.startsWith("API-KEY ")) {
					final String apiKey = authHeader.substring(8);
					var authToken = new AppAuthenticationKey(apiKey);
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}

			} catch (Exception ex) {
				throw new ServletException(ex.getMessage());
			}
		}

		filterChain.doFilter(request, response);
	}

}
