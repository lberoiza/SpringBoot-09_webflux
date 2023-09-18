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
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

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

  // Cuando los datos tienen un Delay
  // se pueden retornar usando un ReactiveDataDriverContextVariable
  // Esta variable puede almacenar los tados en un buffer definido
  // la cantidad de elementos
  @GetMapping("/list-datadriver")
  public String getProductListDataDriver(Model model) {
    Flux<Product> productFlux = productService.findAll()
        // se simula un delay en los datos
        // cada dato del flujo necesita medio segundo de procesamiento
        // antes de ser retornado
        .delayElements(Duration.ofMillis(500));

    productFlux.subscribe(product -> log.info("Show Data Driver: " + product.getName()));


    // esta variable almacena un buffer de 1 elemento antes de retornarlo
    // de esta forma la pagina se cargará de inmediato, pero irá
    // mostrando los datos de a poco, de 2 en 2
    // es decir la pagina tardará 1 segundo en mostrar grupos de 2 productos
    // (cada producto tarda medio segundo y junta 2 antes de mostrarlos)
    ReactiveDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(productFlux, 2);

    model.addAttribute("productFlux", dataDriver);
    model.addAttribute("title", "List of Producs using Flux");
    return "product/list";
  }


  @GetMapping("/list-full")
  public String getProductListFull(Model model) {
    Flux<Product> productFlux = productService.findAll()
        .map(product -> {
          product.setName(product.getName().toUpperCase());
          return product;
        }).repeat(2500);

    model.addAttribute("productFlux", productFlux);
    model.addAttribute("title", "List of Producs using Flux");
    return "product/list";
  }

  @GetMapping("/list-chunked")
  public String getProductListChunked(Model model) {
    Flux<Product> productFlux = productService.findAll()
        .map(product -> {
          product.setName(product.getName().toUpperCase());
          return product;
        }).repeat(2500);

    model.addAttribute("productFlux", productFlux);
    model.addAttribute("title", "List of Producs using Flux");
    return "product/list-chunked";
  }

}
