package com.ipn.mx.springbootwebceroaexperto.user.infrastructure.authentication;

import com.ipn.mx.springbootwebceroaexperto.common.infrastructure.service.JwtService;
import com.ipn.mx.springbootwebceroaexperto.user.domain.port.AuthenticationPort;
import com.ipn.mx.springbootwebceroaexperto.user.infrastructure.database.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationPort {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String authenticate(String username, String password) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserEntity entity = (UserEntity) authenticate.getPrincipal();
        return jwtService.generateToken(entity);
    }
}
