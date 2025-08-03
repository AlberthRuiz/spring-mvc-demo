package edu.pe.cibertec.spring_mvc_demo.controller;

import edu.pe.cibertec.spring_mvc_demo.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
    @GetMapping
    public String listProducts(){
        return  "products/list";
    }
    @GetMapping("/new")
    public String showCreateProduct(Model model){
        model.addAttribute("product", new Product());
        //model.addAttribute("categories", null);
        model.addAttribute("pageTitle", "Create new Product");
        return "products/form";
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
