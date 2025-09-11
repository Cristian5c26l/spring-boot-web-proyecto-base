package com.ipn.mx.springbootwebceroaexperto.user.application.command.register;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.RequestHandler;
import com.ipn.mx.springbootwebceroaexperto.user.domain.User;
import com.ipn.mx.springbootwebceroaexperto.user.domain.UserRole;
import com.ipn.mx.springbootwebceroaexperto.user.domain.port.AuthenticationPort;
import com.ipn.mx.springbootwebceroaexperto.user.domain.port.PasswordEncoderPort;
import com.ipn.mx.springbootwebceroaexperto.user.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserHandler implements RequestHandler<RegisterUserRequest, RegisterUserResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoderPort;
    private final AuthenticationPort authenticationPort;

    @Override
    public RegisterUserResponse handle(RegisterUserRequest request) {

        boolean existsByEmail = userRepository.existsByEmail(request.getEmail());

        if (existsByEmail) {
            throw new RuntimeException("El email ya existe");
        }

        String encoded = passwordEncoderPort.encode(request.getPassword());
        User user = User.builder()
                .email(request.getEmail())
                .password(encoded)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(UserRole.USER)
                .build();

        userRepository.upsert(user);

        String token = authenticationPort.authenticate(request.getEmail(), request.getPassword());

        return new RegisterUserResponse(token);
    }

    @Override
    public Class<RegisterUserRequest> getRequestType() {
        return RegisterUserRequest.class;
    }
}
