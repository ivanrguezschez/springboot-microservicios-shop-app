package com.irs.productsservice.controllers;

import com.irs.productsservice.model.dtos.ProductRequest;
import com.irs.productsservice.model.dtos.ProductResponse;
import com.irs.productsservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /*
      {
          "sku": "000001",
          "name": "PC gamer",
          "description": "Best PC",
          "price": 3210,
          "status": true
      }
      {
          "sku": "000002",
          "name": "PC gamer 2",
          "description": "Best PC 2",
          "price": 3212,
          "status": true
      }
   */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody ProductRequest productRequest) {
        this.productService.addProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return this.productService.getAllProducts();
    }
}
