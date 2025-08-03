package edu.pe.cibertec.spring_mvc_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    @GetMapping
    public String listProducts(){
        return  "";
    }
    @GetMapping("/new")
    public String showCreateProduct(){
        return "";
    }

    @PostMapping
    public String createProduct(){
        return  "";
    }

    @GetMapping("/{id}/edit")
    public  String showEditProduct(){
        return "";
    }

    @PostMapping("/{id}")
    public String updateProduct(){
        return "";
    }

    @GetMapping("/{id}")
    public String viewProduct(){
        return "";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(){
        return "";
    }

}
