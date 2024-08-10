package com.cursos.spring.security.service;

import com.cursos.spring.security.entity.User;

public interface UserService {

    User findByUsername( String username );

}