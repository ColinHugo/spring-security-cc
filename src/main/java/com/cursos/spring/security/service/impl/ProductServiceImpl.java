package com.cursos.spring.security.service.impl;

import com.cursos.spring.security.entity.Product;
import com.cursos.spring.security.exception.ResourceNotFoundException;
import com.cursos.spring.security.repository.ProductRepository;
import com.cursos.spring.security.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List< Product > findAll() {

        List< Product > products = productRepository.findAll();

        if ( products.isEmpty() ) {
            throw new ResourceNotFoundException( "No hay productos registrados" );
        }

        return products;

    }

    @Override
    public Product findById( Long id ) {
        return productRepository
                .findById( id )
                .orElseThrow( () -> new ResourceNotFoundException( "Producto no encontrado con el id: " + id ) );
    }

    @Override
    public Product save( Product product ) {
        return productRepository.save( product );
    }

}