package com.inventariocolorprint.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // --- GERENCIA ---
        UserDetails jorge = User.withDefaultPasswordEncoder()
                .username("jorge")
                .password("gerencia123")
                .roles("ADMIN")
                .build();

        // --- VENTAS ---
        UserDetails pamela = User.withDefaultPasswordEncoder()
                .username("pamela")
                .password("ventas123")
                .roles("USER")
                .build();

        UserDetails anita = User.withDefaultPasswordEncoder()
                .username("anita")
                .password("ventas123")
                .roles("USER")
                .build();

        // --- DISEÑO / PRE-PRENSA (Tú y Alex con la misma clave) ---
        UserDetails matias = User.withDefaultPasswordEncoder()
                .username("matias")
                .password("prensa123")
                .roles("USER")
                .build();

        UserDetails alex = User.withDefaultPasswordEncoder()
                .username("alex")
                .password("prensa123") // Misma clave que tú
                .roles("USER")
                .build();

        // --- PRODUCCIÓN (Nuevos) ---
        UserDetails german = User.withDefaultPasswordEncoder()
                .username("german")
                .password("produccion123")
                .roles("USER")
                .build();

        UserDetails gloria = User.withDefaultPasswordEncoder()
                .username("gloria")
                .password("produccion123")
                .roles("USER")
                .build();

        // Agregamos a TODOS a la lista de acceso
        return new InMemoryUserDetailsManager(jorge, pamela, anita, matias, alex, german, gloria);
    }
}