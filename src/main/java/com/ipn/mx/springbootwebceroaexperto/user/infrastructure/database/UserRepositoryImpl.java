package com.ipn.mx.springbootwebceroaexperto.user.infrastructure.database;

import com.ipn.mx.springbootwebceroaexperto.user.domain.User;
import com.ipn.mx.springbootwebceroaexperto.user.domain.port.UserRepository;
import com.ipn.mx.springbootwebceroaexperto.user.infrastructure.database.entity.UserEntity;
import com.ipn.mx.springbootwebceroaexperto.user.infrastructure.database.mapper.UserEntityMapper;
import com.ipn.mx.springbootwebceroaexperto.user.infrastructure.database.repository.QueryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final QueryUserRepository queryUserRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public boolean existsByEmail(String email) {
        return queryUserRepository.findByEmail(email).isPresent();// queryUserRepository.findByEmail(email) retorna un Optional<UserEntity>
    }

    @Override
    public User upsert(User user) {

        UserEntity userEntity = userEntityMapper.mapToUserEntity(user);
        UserEntity saved = queryUserRepository.save(userEntity);
        return userEntityMapper.mapToUser(saved);
    }
}
