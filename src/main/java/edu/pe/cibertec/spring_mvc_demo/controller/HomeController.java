package edu.pe.cibertec.spring_mvc_demo.controller;

import edu.pe.cibertec.spring_mvc_demo.service.CategoryService;
import edu.pe.cibertec.spring_mvc_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

   @Autowired
    private ProductService productService;
   @Autowired
    private CategoryService categoryService;

   @GetMapping("/")
   public String home(){
       return "index";

   }


}
