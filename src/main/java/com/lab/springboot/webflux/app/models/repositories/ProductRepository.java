package com.lab.springboot.webflux.app.models.repositories;

import com.lab.springboot.webflux.app.models.documents.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
