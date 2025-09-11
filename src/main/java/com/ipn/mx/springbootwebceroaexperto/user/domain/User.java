package com.ipn.mx.springbootwebceroaexperto.user.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Long id;
    private String email;
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;
}
