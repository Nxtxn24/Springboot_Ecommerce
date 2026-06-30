package com.example.app.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .cors(cors -> {})
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/auth/**").permitAll()

                            // Public: view products
                            .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/ratings/**").hasRole("USER")

                            // USER access
                            .requestMatchers("/cart/**").hasRole("USER")
                            .requestMatchers(HttpMethod.POST, "/orders/checkout").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/orders/**").hasRole("USER")

                            // ADMIN only
                            .requestMatchers(HttpMethod.POST, "/api/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("ADMIN")

                            // (Optional admin-only order actions)
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PATCH, "/orders/**").hasRole("ADMIN")

                            .anyRequest().authenticated()
                    )
                    .addFilterBefore(jwtFilter,
                            UsernamePasswordAuthenticationFilter.class)
                    .build();
        }
}
