package com.ipn.mx.springbootwebceroaexperto.user.domain.port;

import com.ipn.mx.springbootwebceroaexperto.user.domain.User;

public interface UserRepository {


    boolean existsByEmail(String email);

    User upsert(User user);
}
