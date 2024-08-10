package com.cursos.spring.security.repository;

import com.cursos.spring.security.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository< Product, Long > {
}