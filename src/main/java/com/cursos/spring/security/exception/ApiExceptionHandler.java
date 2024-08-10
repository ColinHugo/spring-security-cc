package com.cursos.spring.security.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler( ResourceNotFoundException.class )
    public ResponseEntity< ApiException > handleResourceNotFoundException( ResourceNotFoundException exception, HttpServletRequest request ) {

        ApiException apiException = ApiException
                .builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.NOT_FOUND.value() )
                .message( "Recurso no encontrado: " + exception.getMessage() )
                .details( request.getRequestURL().toString() )
                .build();

        log.error( "Descripción del error: " + apiException );

        return new ResponseEntity<>( apiException, HttpStatus.NOT_FOUND );

    }

    @ExceptionHandler( MethodArgumentNotValidException.class )
    public ResponseEntity< ApiException > handleInvalidArguments( MethodArgumentNotValidException exception, HttpServletRequest request ) {

        Map< String, String > errors = new HashMap<>();

        exception
                .getBindingResult()
                .getFieldErrors()
                .forEach( error -> {

                    String nameField = error.getField();
                    String errorMessage = error.getDefaultMessage();

                    errors.put( nameField, errorMessage );

                } );

        ApiException apiException = ApiException
                .builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.BAD_REQUEST.value() )
                .message( "Se encontraron errores en los siguientes campos: " + errors )
                .details( request.getRequestURL().toString() )
                .build();

        log.error( "Descripción del error: " + apiException );

        return new ResponseEntity<>( apiException, HttpStatus.BAD_REQUEST );

    }

    @ExceptionHandler( BadCredentialsException.class )
    public ResponseEntity< ApiException > handleBadCredentialsException( BadCredentialsException exception, HttpServletRequest request ) {

        ApiException apiException = ApiException
                .builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.UNAUTHORIZED.value() )
                .message( "Usuario o contraseña incorrectas: " + exception.getMessage() )
                .details( request.getRequestURL().toString() )
                .build();

        log.error( "Descripción del error: " + apiException );

        return new ResponseEntity<>( apiException, HttpStatus.UNAUTHORIZED );

    }

    @ExceptionHandler( AccessDeniedException.class )
    public ResponseEntity< ApiException > handleAccessDeniedException( AccessDeniedException exception, HttpServletRequest request ) {

        ApiException apiException = ApiException
                .builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.FORBIDDEN.value() )
                .message( "No tienes permiso para este recurso: " + exception.getMessage() )
                .details( request.getRequestURL().toString() )
                .build();

        log.error( "Descripción del error: " + apiException );

        return new ResponseEntity<>( apiException, HttpStatus.FORBIDDEN );

    }

    @ExceptionHandler( SignatureException.class )
    public ResponseEntity< ApiException > handleSignatureException( SignatureException exception, HttpServletRequest request ) {

        ApiException apiException = ApiException
                .builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.BAD_REQUEST.value() )
                .message( "Token inválido: " + exception.getMessage() )
                .details( request.getRequestURL().toString() )
                .build();

        log.error( "Descripción del error: " + apiException );

        return new ResponseEntity<>( apiException, HttpStatus.BAD_REQUEST );

    }

    @ExceptionHandler( ExpiredJwtException.class )
    public ResponseEntity< ApiException > handleExpiredJwtException( ExpiredJwtException exception, HttpServletRequest request ) {

        ApiException apiException = ApiException
                .builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.BAD_REQUEST.value() )
                .message( "El token ha expirado: " + exception.getMessage() )
                .details( request.getRequestURL().toString() )
                .build();

        log.error( "Descripción del error: " + apiException );

        return new ResponseEntity<>( apiException, HttpStatus.BAD_REQUEST );

    }

    @ExceptionHandler( Exception.class )
    public ResponseEntity< ApiException > handleGlobalException( Exception exception, HttpServletRequest request ) {

        ApiException apiException = ApiException
                .builder()
                .timestamp( LocalDateTime.now() )
                .status( HttpStatus.INTERNAL_SERVER_ERROR.value() )
                .message( "Se produjo error interno en el sistema " + exception.getMessage() )
                .details( request.getRequestURL().toString() )
                .build();

        log.error( "Descripción del error: " + apiException );

        return new ResponseEntity<>( apiException, HttpStatus.INTERNAL_SERVER_ERROR );

    }

}