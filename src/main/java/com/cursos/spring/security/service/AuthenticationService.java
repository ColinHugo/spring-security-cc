package com.cursos.spring.security.service;

import com.cursos.spring.security.dto.AuthenticationRequest;
import com.cursos.spring.security.dto.AuthenticationResponse;
import com.cursos.spring.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationResponse login( AuthenticationRequest authRequest ) {

        Authentication authentication = new UsernamePasswordAuthenticationToken( authRequest.getUsername(), authRequest.getPassword() );

        authenticationManager.authenticate( authentication );

        User user = userService.findByUsername( authRequest.getUsername() );

        String jwt = jwtService.generateToken( user, generateExtraClaims( user ) );

        return new AuthenticationResponse( jwt );

    }

    private Map< String, Object > generateExtraClaims( User user ) {

        Map< String, Object > extraClaims = new HashMap<>();
        extraClaims.put( "name", user.getName() );
        extraClaims.put( "role", user.getRole().name() );
        extraClaims.put( "permissions", user.getAuthorities() );

        return extraClaims;

    }

}