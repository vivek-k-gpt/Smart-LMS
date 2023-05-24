package com.gl.smartlms.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.gl.smartlms.filter.JwtFilter;
import com.gl.smartlms.service.UserInfoUserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// ==============================================================
// = Security Configuration class
// =================================================================
@Order(1)
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private JwtFilter authFilter;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserInfoUserDetailsService();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf().disable().authorizeHttpRequests().requestMatchers(AUTH_WHITELIST).permitAll().and()
				.authorizeHttpRequests().requestMatchers("api-admin/**").hasRole("ADMIN").and().authorizeHttpRequests()
				.requestMatchers("api-all/**").hasAnyRole("ADMIN", "USER", "LIBRARIAN").and().authorizeHttpRequests()
				.requestMatchers("api-librarian/**").hasRole("LIBRARIAN").and().authorizeHttpRequests()
				.requestMatchers("api-admin-librarian/**").hasAnyRole("LIBRARIAN", "ADMIN").and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();

		//

//    	
	}

	public static final String[] AUTH_WHITELIST = { "/api/v1/auth/**", "/v3/api-docs/**", "/v3/api-docs.yaml",
			"/swagger-ui/**", "/swagger-ui.html", "user/register", "/user/authenticate", "/webjars/**",
			"/swaggger-ui/**", "/configuration/security", "confifguration-ui", "/swagger-resources",
			"/swagger-resources/**", "/v2/api-docs", "/v3/api-docs","/refreshtoken"

	};

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
