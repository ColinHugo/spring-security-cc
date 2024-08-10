package com.cursos.spring.security.service.impl;

import com.cursos.spring.security.entity.User;
import com.cursos.spring.security.repository.UserRepository;
import com.cursos.spring.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername( String username ) {
        return userRepository
                .findByUsername( username )
                .orElseThrow( () -> new UsernameNotFoundException( "Usuario no encontrado" ) );
    }

}