package com.whatsapp.backend.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers("/api/v1/**").authenticated().anyRequest().permitAll())
				.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).csrf(csrf -> {
					try {
						csrf.disable().cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
							@Override
							public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
								CorsConfiguration cfg = new CorsConfiguration();
								cfg.setAllowedOriginPatterns(Arrays.asList("http://127.0.0.1:3000","http://localhost:3000","https://whatsapp-clone-web.vercel.app"));
								cfg.setAllowedMethods(Collections.singletonList("*"));
								cfg.setAllowCredentials(true);
								cfg.setAllowedHeaders(Collections.singletonList("*"));
								cfg.setExposedHeaders(Arrays.asList("Authorization"));
								cfg.setMaxAge(3600L);
								return cfg;
							}
						}));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).formLogin(withDefaults()).httpBasic(withDefaults());
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
