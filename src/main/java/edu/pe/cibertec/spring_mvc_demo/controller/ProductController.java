package edu.pe.cibertec.spring_mvc_demo.controller;

import edu.pe.cibertec.spring_mvc_demo.entity.Product;
import edu.pe.cibertec.spring_mvc_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    List<String> catergories = new ArrayList<>();
    @GetMapping
    public String listProducts(Model model){
        List<Product> products = productService.getAllProduct();
        model.addAttribute("products", products);
        model.addAttribute("categories", catergories); // Falta completar
        model.addAttribute("pageTitle", "Products List");
        return  "products/list";
    }
    @GetMapping("/new")
    public String showCreateProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categories", catergories); // Falta completar
        model.addAttribute("pageTitle", "Create new Product");
        return "products/form";
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product, Model model ){
        try{
            product.setEstado(true);
            productService.createProduct(product);

            model.addAttribute("successMessage", "New product was added.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something is wrong with request. Ex:"+ e.getMessage());
            model.addAttribute("product", product);
            model.addAttribute("categories", catergories);
        }
        return  "products/form";
    }

    @GetMapping("/{id}/edit")
    public  String showEditProduct(@PathVariable Long id, Model model){
        Optional<Product> product = productService.getProductById(id);
        if(product.isEmpty()){
            model.addAttribute("errorMessage", "Product not found.");
            return  "redirect:/products";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", catergories);
        model.addAttribute("isEdit", true);
        return "products/form";
    }
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id,@ModelAttribute Product product, Model model){
        try{
            productService.updateProduct(id, product);
            model.addAttribute("successMessage", "Produc was updated.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something was wrong with request. Ex:"+ e.getMessage());
            model.addAttribute("product", product);
            model.addAttribute("categories", catergories);
            model.addAttribute("isEdit", true);
        }
        return  "products/form";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id , Model model){
        Optional<Product> product = productService.getProductById(id);
        if(product.isEmpty()){
            model.addAttribute("errorMessage", "Product not found.");
            return  "redirect:/products";
        }
        model.addAttribute("product", product);
        return "product/detail";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Model model){
        try{
            boolean deleted = productService.deleteProduct(id);
            if(deleted){
                model.addAttribute("successMessage", "Product was deleted.");
            }else{
                model.addAttribute("errorMessage", "Error on delete operation.");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage",
                    "Something was wrong with your request. Ex: "+e.getMessage());
        }
        return "redirect:/products";
    }

}
