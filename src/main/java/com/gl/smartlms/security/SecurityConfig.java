package com.gl.smartlms.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import com.gl.smartlms.service.UserInfoUserDetailsService;


@Order(1)
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	
	
	

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
	
    
	@Bean
	public UserDetailsService userDetailsService() {
	return new UserInfoUserDetailsService();
	}
	
	
//	 .antMatchers("/**").hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_LIBRARIAN).anyRequest()
////.authenticated()   .antMatchers("/admin/**").hasRole("ADMIN")
//.antMatchers("/user/**").hasRole("USER")
//.antMatchers("/**").permitAll()
	

	
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	
       http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("user/register").permitAll().and()
                .authorizeHttpRequests().requestMatchers("api-admin/**").hasRole("ADMIN").and()
                .authorizeHttpRequests().requestMatchers("api-all/**").hasAnyRole("ADMIN","USER","LIBRARIAN").and()
                
                .authorizeHttpRequests().requestMatchers("/api-issue/**").hasRole("USER")
             
                .and()
                .authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/api-book/list").hasRole("USER").and().httpBasic();
        return http.build();
//    	return http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated().and().
    }
    
    
    
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
	
}
