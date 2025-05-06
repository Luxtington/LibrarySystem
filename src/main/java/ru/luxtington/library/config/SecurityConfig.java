package ru.luxtington.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers("/lib/auth/**", "/auth/**").permitAll()
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/books/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN", "ROLE_READER")
                .requestMatchers("/persons/*/change-role").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/persons/*/delete").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/persons/*/update").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/persons/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_LIBRARIAN")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/lib/auth/login")
                .loginProcessingUrl("/lib/auth/login")
                .defaultSuccessUrl("/lib/index", true)
                .failureUrl("/lib/auth/login?error=true")
            .and()
                .logout()
                .logoutUrl("/lib/auth/logout")
                .logoutSuccessUrl("/lib/auth/login")
            .and()
                .csrf().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
} 