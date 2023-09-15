package com.lab.springboot.webflux.app.services;

import com.lab.springboot.webflux.app.models.documents.Product;
import com.lab.springboot.webflux.app.models.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

  private final ProductRepository repository;

  @Autowired
  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public Mono<Product> save(Product product) {
    return this.repository.save(product);
  }

  public Flux<Product> findAll(){
    return this.repository.findAll();
  }

}
