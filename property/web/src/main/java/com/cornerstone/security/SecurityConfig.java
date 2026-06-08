package com.cornerstone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()

                        .requestMatchers("/users/**").hasRole("SUPER_ADMIN")

                        .requestMatchers("/work-orders/create",
                                "/work-orders/edit/**",
                                "/work-orders/cancel/**")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN")

                        .requestMatchers("/work-orders",
                                "/work-orders/details/**")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN", "USER")

                        .requestMatchers("/work-orders/my",
                                "/work-orders/status/**",
                                "/work-orders/status-with-notes/**")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN", "USER")

                        .requestMatchers("/units/delete/**",
                                "/tenants/delete/**",
                                "/leases/end/**",
                                "/unit-history/delete/**")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN")

                        .requestMatchers("/units/**",
                                "/tenants/**",
                                "/leases/**",
                                "/unit-history/**",
                                "/managers/**")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN", "USER")

                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}