package com.ipn.mx.springbootwebceroaexperto.common.infrastructure.config;

import com.ipn.mx.springbootwebceroaexperto.common.infrastructure.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
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
                .formLogin(Customizer.withDefaults())// habilitar formulario de usuario/contraseña al acceder a una ruta proegida desde el navegador web
                .oauth2Login(Customizer.withDefaults())// habilitar la posibilidad de que en el formulario, el usuario pueda acceder a la ruta solicitada autentixandose con algun proveedor configurado (como el de github)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
