package com.cursos.spring.security.controller;

import com.cursos.spring.security.dto.AuthenticationRequest;
import com.cursos.spring.security.dto.AuthenticationResponse;
import com.cursos.spring.security.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping( "/auth" )
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping( "/authenticate" )
    public ResponseEntity< AuthenticationResponse > login( @Valid @RequestBody AuthenticationRequest authRequest ) {

        AuthenticationResponse jwt = authenticationService.login( authRequest );

        return ResponseEntity.ok( jwt );
    }

    @GetMapping( "/public-access" )
    public String publicAccessEndpoint() {
        return "Este endpoint es público";
    }

}