package com.lab.springboot.webflux.app.models.documents;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
@Getter
@Setter
public class Product {

  @Id
  private String id;
  private String name;
  private String description;
  private Double price;
  @Setter(AccessLevel.NONE)
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Product() {
  }

  public Product(String name, String description, Double price) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.createdAt = LocalDateTime.now();
  }
}
