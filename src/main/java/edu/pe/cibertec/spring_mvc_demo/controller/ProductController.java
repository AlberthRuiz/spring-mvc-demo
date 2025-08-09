package edu.pe.cibertec.spring_mvc_demo.controller;

import edu.pe.cibertec.spring_mvc_demo.entity.Product;
import edu.pe.cibertec.spring_mvc_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        List<String> categories = productService.getAllCategories();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Gestión de Productos");

        return "products/list"; // Retorna la vista list.html
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("pageTitle", "Crear Nuevo Producto");

        return "products/form"; // Retorna la vista form.html
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product, Model model) {
        try {
            product.setEstado(true);
            productService.createProduct(product);

            model.addAttribute("successMessage", "Producto creado exitosamente");
            return "redirect:/products"; // Redirecciona a la lista
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al crear producto: " + e.getMessage());
            model.addAttribute("product", product);
            model.addAttribute("categories", productService.getAllCategories());
            return "products/form"; // Vuelve al formulario con error
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);

        if (product.isEmpty()) {
            model.addAttribute("errorMessage", "Producto no encontrado");
            return "redirect:/products";
        }

        model.addAttribute("product", product.get());
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("pageTitle", "Editar Producto");
        model.addAttribute("isEdit", true);

        return "products/form";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, Model model) {
        try {
            productService.updateProduct(id, product);
            model.addAttribute("successMessage", "Producto actualizado exitosamente");
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar producto: " + e.getMessage());
            model.addAttribute("product", product);
            model.addAttribute("categories", productService.getAllCategories());
            model.addAttribute("isEdit", true);
            return "products/form";
        }
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);

        if (product.isEmpty()) {
            model.addAttribute("errorMessage", "Producto no encontrado");
            return "redirect:/products";
        }

        model.addAttribute("product", product.get());
        model.addAttribute("pageTitle", "Detalles del Producto");

        return "products/detail";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Model model) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                model.addAttribute("successMessage", "Producto eliminado exitosamente");
            } else {
                model.addAttribute("errorMessage", "No se pudo eliminar el producto");
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al eliminar producto: " + e.getMessage());
        }

        return "redirect:/products";
    }


}