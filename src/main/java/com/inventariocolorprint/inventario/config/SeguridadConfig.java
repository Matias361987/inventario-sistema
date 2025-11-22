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
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Permitir estilos
                        .anyRequest().authenticated() // Todo lo demás requiere login
                )
                .formLogin((form) -> form
                        .loginPage("/login") // Nuestra página personalizada
                        .permitAll()
                )
                .logout((logout) -> logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // VENTAS
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

        // GERENCIA
        UserDetails jorge = User.withDefaultPasswordEncoder()
                .username("jorge")
                .password("gerencia123") // Contraseña para Jorge
                .roles("ADMIN")
                .build();

        // PRE-PRENSA
        UserDetails matias = User.withDefaultPasswordEncoder()
                .username("matias")
                .password("prensa123")
                .roles("USER")
                .build();

        UserDetails alex = User.withDefaultPasswordEncoder()
                .username("alex")
                .password("prensa123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(pamela, anita, jorge, matias, alex);
    }
}