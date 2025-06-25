package com.superbleep.rvgamvc.config;

import com.superbleep.rvgamvc.services.security.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("postgres")
public class SecurityConfig {
    private JpaUserDetailsService userDetailsService;

    public SecurityConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/emulators", "/platforms", "/games", "/gameVersions", "/error").hasAnyRole("REGULAR", "MODERATOR")
                        .requestMatchers("/*/add", "/*/edit/*", "/*/remove/*", "/*/create", "/*/update", "/*/delete").hasRole("MODERATOR")
                        .requestMatchers("/webjars/**", "/login", "/resources/**", "/performLogin", "/actuator/*").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .formLogin(formLogin ->
                    formLogin
                            .loginPage("/login")
                            .loginProcessingUrl("/performLogin")
                            .defaultSuccessUrl("/", true)
                            .permitAll()
                )
                .logout(logout ->
                    logout
                            .logoutUrl("/performLogout")
                            .permitAll()
                )
                .exceptionHandling(e -> e.accessDeniedPage("/accessDenied"))
                .build();
    }
}
