package com.restaurante.sistema.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // Recursos estáticos públicos
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                // Login público
                .requestMatchers("/login").permitAll()
                
                // --- REGLAS DE GERENCIA ---
                .requestMatchers("/usuarios/**").hasRole("GERENTE")
                .requestMatchers("/gerente/**").hasRole("GERENTE")
                .requestMatchers("/vistas/gerente/**").hasRole("GERENTE") // Aseguramos las vistas antiguas de gerente

                // --- REGLAS DE PEDIDOS (NUEVO) ---
                // Importante: Permitimos entrar a Meseros y Gerentes a todo lo que sea /pedido/...
                .requestMatchers("/pedido/**").hasAnyRole("MESERO", "GERENTE")
                .requestMatchers("/vistas/pedidos/**").hasAnyRole("MESERO", "GERENTE")

                // Cualquier otra cosa requiere estar logueado
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/", true) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}