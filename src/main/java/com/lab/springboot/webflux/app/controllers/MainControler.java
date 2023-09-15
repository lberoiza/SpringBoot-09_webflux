package com.lab.springboot.webflux.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControler {

  public static final String START_POINT = "/product/list";


  @GetMapping("/")
  public String startPoint(){
    return "redirect:" + START_POINT;
  }

}
