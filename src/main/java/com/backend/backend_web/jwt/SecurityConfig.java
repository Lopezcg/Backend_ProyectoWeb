package com.backend.backend_web.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/arrendador/login").permitAll()
                        .requestMatchers("/arrendatario/login").permitAll()
                        .requestMatchers("/arrendador/bypropiedad/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/propiedad").permitAll()
                        .requestMatchers(HttpMethod.GET, "/propiedad/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/propiedad/arrendador/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/calificacion").permitAll()

                        // .requestMatchers(HttpMethod.POST, "/arrendador").permitAll() // Permite solo
                        // POST para
                        // TOCA PONER SOLO LOS NECESARIOS

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
