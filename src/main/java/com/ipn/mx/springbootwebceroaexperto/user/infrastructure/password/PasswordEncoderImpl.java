package com.ipn.mx.springbootwebceroaexperto.user.infrastructure.password;

import com.ipn.mx.springbootwebceroaexperto.user.domain.port.PasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderImpl implements PasswordEncoderPort {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}
