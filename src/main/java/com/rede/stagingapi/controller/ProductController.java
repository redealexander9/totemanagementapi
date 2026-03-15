package com.rede.stagingapi.controller;
import com.rede.stagingapi.exception.ProductNotFoundException;
import com.rede.stagingapi.model.Product;
import com.rede.stagingapi.model.ShelfLocation;
import com.rede.stagingapi.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/{upc}")
    public Product getProduct(@PathVariable String upc){
        return productRepository.findById(upc).orElseThrow(() -> new ProductNotFoundException("Product has not been created yet"));
    }

    @GetMapping("/{upc}/location")
    public ShelfLocation getLocationOfProduct(@PathVariable String upc){
        Product product = productRepository.findById(upc).orElseThrow(() -> new ProductNotFoundException("Product has not been created yet"));
        return product.getLocation();
    }
}
