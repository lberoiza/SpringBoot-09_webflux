package com.lab.springboot.webflux.app.utils;

import com.lab.springboot.webflux.app.models.documents.Product;
import com.lab.springboot.webflux.app.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataImporter {

  private static final Logger log = LoggerFactory.getLogger(DataImporter.class);

  private final ProductService productService;
  private final ReactiveMongoTemplate mongoTemplate;

  @Autowired
  public DataImporter(ProductService productService, ReactiveMongoTemplate mongoTemplate) {
    this.productService = productService;
    this.mongoTemplate = mongoTemplate;
  }

  public void importInitialData() {
    dropAndImportProducts();
  }


  private void dropAndImportProducts() {
    mongoTemplate.dropCollection("productos").subscribe();

    Flux.fromIterable(this.getProductList())
        .flatMap(productService::save)
        .subscribe(
            product -> {
              String message = String.format("Insert: %s %.2f", product.getName(), product.getPrice());
              log.info(message);
            }
        );
  }

  private List<Product> getProductList() {
    List<Product> productList = new ArrayList<>();
    productList.add(new Product("Panasonic LCD Screen", "35 inch Television wide screen", 750.00));
    productList.add(new Product("Sony Camera", "Digital Camera Sony DSC-W320B", 350.00));
    productList.add(new Product("Ipod", "Apple Ipod Shuffle", 300.00));
    productList.add(new Product("Sony Vaio", "Notebook Sony Vaio 15 inch", 1500.00));
    productList.add(new Product("Printer HP", "Hewlett Packard Multiprinter", 279.89));
    productList.add(new Product("Bianchi Bicycle", "Bicycle Bianchi 17 inch wheels", 1359.89));
    productList.add(new Product("Mica Desk", "Desk for office", 435.00));
    return productList;
  }

}
