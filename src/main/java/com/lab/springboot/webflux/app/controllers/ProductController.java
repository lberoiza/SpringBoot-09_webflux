package com.lab.springboot.webflux.app.controllers;

import com.lab.springboot.webflux.app.models.documents.Product;
import com.lab.springboot.webflux.app.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/product")
public class ProductController {

  private static final Logger log = LoggerFactory.getLogger(ProductController.class);

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/list")
  public String getProductList(Model model) {
    Flux<Product> productFlux = productService.findAll()
        .map(product -> {
          product.setName(product.getName().toUpperCase());
          return product;
        });

    productFlux.subscribe(product -> log.info("Show: " + product.getName()));

    model.addAttribute("productFlux", productFlux);
    model.addAttribute("title", "List of Producs using Flux");
    return "product/list";
  }

}
