package com.ipn.mx.springbootwebceroaexperto.user.application.command.login;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.RequestHandler;
import org.springframework.stereotype.Service;

@Service//podria ser @Component
public class LoginUserHandler implements RequestHandler<LoginUserRequest, LoginUserResponse> {
    @Override
    public LoginUserResponse handle(LoginUserRequest request) {
        return null;
    }

    @Override
    public Class<LoginUserRequest> getRequestType() {
        return LoginUserRequest.class;
    }
}
