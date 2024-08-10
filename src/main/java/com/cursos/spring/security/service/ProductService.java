package com.cursos.spring.security.service;

import com.cursos.spring.security.entity.Product;

import java.util.List;

public interface ProductService {

    List< Product > findAll();

    Product findById( Long id );

    Product save( Product product );

}