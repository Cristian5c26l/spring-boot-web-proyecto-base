package com.ipn.mx.springbootwebceroaexperto.common.infrastructure.filters;

import com.ipn.mx.springbootwebceroaexperto.common.infrastructure.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("No Authorization header present in request. No token found in request.");
            filterChain.doFilter(request, response);// dejar pasar la peticion al filtro de UsernamePasswordAuthenticationFilter.class
            return;
        }

        String token = authorizationHeader.substring(7);

        boolean isTokenExpired = jwtService.isTokenExpired(token);
        boolean canBeTokenRenewed = jwtService.canBeTokenRenewed(token);

        if (isTokenExpired && !canBeTokenRenewed) {
            log.error("Token expired");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        boolean isValidToken = jwtService.isValidToken(token, userDetails);

        if (!isValidToken || SecurityContextHolder.getContext().getAuthentication() != null) {
            log.error("Invalid token or user already authenticated");
            filterChain.doFilter(request, response);
            return;
        }

        //log.info("Username {} ", username);

        if (isTokenExpired && canBeTokenRenewed) {
            String renewToken = jwtService.renewToken(token, userDetails);
            response.setHeader("Authorization", "Bearer " + renewToken);
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
