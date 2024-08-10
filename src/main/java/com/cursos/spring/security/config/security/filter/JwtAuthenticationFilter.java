package com.cursos.spring.security.config.security.filter;

import com.cursos.spring.security.entity.User;
import com.cursos.spring.security.service.JwtService;
import com.cursos.spring.security.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {

        // 1.- Obtener el header que tiene el jwt
        String authHeader = request.getHeader( "Authorization" ); // Bearer < jwt >

        if ( authHeader == null || !authHeader.startsWith( "Bearer " ) ) {
            filterChain.doFilter( request, response );
            return;
        }

        // 2.- Obtener el jwt
        String jwt = authHeader.split( " " )[ 1 ];

        // 3.- Obtener el subject desde el jwt
        String username = jwtService.extractUsername( jwt );

        // 4.- Seterar un objeto Authentication dentro del SecurityContext
        User user = userService.findByUsername( username );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( username, null, user.getAuthorities() );

        SecurityContextHolder.getContext().setAuthentication( authentication );

        // 5 .- Ejecutar el resto de filtros
        filterChain.doFilter( request, response );

    }

}