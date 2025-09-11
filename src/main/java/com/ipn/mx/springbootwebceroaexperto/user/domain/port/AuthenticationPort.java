package com.ipn.mx.springbootwebceroaexperto.user.domain.port;

public interface AuthenticationPort {
    String authenticate(String username, String password);
}
