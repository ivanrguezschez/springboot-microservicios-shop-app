package com.irs.productsservice.repositories;

import com.irs.productsservice.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
