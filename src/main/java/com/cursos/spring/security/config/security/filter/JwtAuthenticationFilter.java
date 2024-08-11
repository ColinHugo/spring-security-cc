package com.cursos.spring.security.config.security.filter;

import com.cursos.spring.security.entity.User;
import com.cursos.spring.security.exception.ApiException;
import com.cursos.spring.security.service.JwtService;
import com.cursos.spring.security.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        try {
            // 1.- Obtener el header que tiene el jwt
            String authHeader = request.getHeader("Authorization"); // Bearer < jwt >

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // 2.- Obtener el jwt
            String jwt = authHeader.split(" ")[1];

            // 3.- Obtener el subject desde el jwt
            String username = jwtService.extractUsername(jwt);

            // 4.- Seterar un objeto Authentication dentro del SecurityContext
            User user = userService.findByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            ApiException apiException = ApiException.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("El token ha expirado: " + e.getMessage())
                    .details(request.getRequestURL().toString())
                    .build();

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(apiException));
            return; // Termina la ejecución aquí
        }

        // 5 .- Ejecutar el resto de filtros
        filterChain.doFilter( request, response );

    }

}