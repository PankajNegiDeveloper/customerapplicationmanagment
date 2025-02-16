package com.rest.cam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.rest.cam.security.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	// ************using security DAO************************
	private CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		super();
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	// ******************************************************

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests((auth) -> auth.requestMatchers("/api/**").authenticated() // we can have n number
																									// if
																									// requestMatchers
						.requestMatchers("/swagger-ui/**").permitAll().anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	// commented out this because we are now doing DAO authentication, for that we
	// have created roles and users entities!
//	@Bean
//	public UserDetailsService userDetails() {
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder()
//				.encode("admin"))
//				.roles("ADMIN")
//				.build();
//		UserDetails user = User.builder()
//				.username("user")
//				.password(passwordEncoder()
//				.encode("user"))
//				.roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(admin, user);
//	}


}
