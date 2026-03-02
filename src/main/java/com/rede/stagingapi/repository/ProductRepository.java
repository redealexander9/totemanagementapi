package com.rede.stagingapi.repository;

import com.rede.stagingapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
