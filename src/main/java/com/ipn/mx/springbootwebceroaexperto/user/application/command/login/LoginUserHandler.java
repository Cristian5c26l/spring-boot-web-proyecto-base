package com.ipn.mx.springbootwebceroaexperto.user.application.command.login;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.RequestHandler;
import com.ipn.mx.springbootwebceroaexperto.user.domain.port.AuthenticationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service//podria ser @Component
@RequiredArgsConstructor
public class LoginUserHandler implements RequestHandler<LoginUserRequest, LoginUserResponse> {

    private final AuthenticationPort authenticationPort;

    @Override
    public LoginUserResponse handle(LoginUserRequest request) {

        String token = authenticationPort.authenticate(request.getEmail(), request.getPassword());

        return new LoginUserResponse(token);

    }

    @Override
    public Class<LoginUserRequest> getRequestType() {
        return LoginUserRequest.class;
    }
}
