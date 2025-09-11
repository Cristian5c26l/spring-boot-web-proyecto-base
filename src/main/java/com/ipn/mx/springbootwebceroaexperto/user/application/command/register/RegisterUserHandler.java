package com.ipn.mx.springbootwebceroaexperto.user.application.command.register;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.RequestHandler;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserHandler implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {
    @Override
    public RegisterUserResponse handle(RegisterUserRequest request) {
        return null;
    }

    @Override
    public Class<RegisterUserRequest> getRequestType() {
        return RegisterUserRequest.class;
    }
}
