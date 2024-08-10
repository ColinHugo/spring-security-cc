package com.cursos.spring.security.controller;

import com.cursos.spring.security.entity.Product;
import com.cursos.spring.security.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/products" )
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity< List < Product > > findAll() {
        return ResponseEntity.ok( productService.findAll() );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity< Product > findById( @PathVariable Long id ) {
        return ResponseEntity.ok( productService.findById( id ) );
    }

    @PostMapping
    public ResponseEntity< Product > createOne( @Valid @RequestBody Product product ) {
        return ResponseEntity.ok( productService.save( product ) );
    }

}