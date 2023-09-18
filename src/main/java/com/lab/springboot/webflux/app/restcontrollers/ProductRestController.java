package com.lab.springboot.webflux.app.restcontrollers;

import com.lab.springboot.webflux.app.controllers.ProductController;
import com.lab.springboot.webflux.app.models.documents.Product;
import com.lab.springboot.webflux.app.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

  private static final Logger log = LoggerFactory.getLogger(ProductRestController.class);

  private final ProductService productService;

  @Autowired
  public ProductRestController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public Flux<Product> getProductList() {
    return productService.findAll()
        .map(product -> {
          product.setName(product.getName().toUpperCase());
          return product;
        })
        .doOnNext(product -> log.info(product.getName()));
  }

  @GetMapping("/{id}")
  public Mono<Product> getProduct(@PathVariable String id) {
    return productService.findAll()
        .filter(product -> product.getId().equals(id))
        .next()
        .doOnNext(product -> log.info(product.getName()));
  }
}
