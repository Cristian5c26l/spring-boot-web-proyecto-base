package com.ipn.mx.springbootwebceroaexperto.common.infrastructure.config;

import com.ipn.mx.springbootwebceroaexperto.common.infrastructure.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
//                                "/api/v1/products/**",
                                        "/api/v1/users/login",
                                        "/api/v1/users/register",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/proxy/**"
                                ).permitAll()// peticiones a las rutas indicadas de arriba pasan primero por el filtro jwtFiler, luego el filtro UsernamePasswordAuthenticationFilter.class
                                .requestMatchers("/actuator/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()// peticiones a otras rutas, deben pasar por el filtro de jwtFilter y por el filtro UsernamePasswordAuthenticationFilter
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// Configura la seguridad sin manejo de sesiones: cada request debe autenticarse de forma independiente (API REST stateless), lo cual se logra con JWT
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
