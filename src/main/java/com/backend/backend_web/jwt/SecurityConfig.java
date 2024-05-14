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
                        // .requestMatchers(HttpMethod.POST, "/arrendador").permitAll() // Permite solo
                        // POST para
                        // TOCA PONER SOLO LOS NECESARIOS
                        .requestMatchers("/arrendador").permitAll()
                        .requestMatchers("/arrendatario").permitAll()
                        .requestMatchers("/calificacion").permitAll()
                        .requestMatchers("/pago").permitAll()
                        .requestMatchers(HttpMethod.POST, "/arrendatario").permitAll() // Permite solo POST para
                        .requestMatchers(HttpMethod.GET, "/propiedad").permitAll() // Permite solo POST para

                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

}
