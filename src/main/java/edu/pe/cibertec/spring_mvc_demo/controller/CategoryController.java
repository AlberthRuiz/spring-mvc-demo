package edu.pe.cibertec.spring_mvc_demo.controller;

import edu.pe.cibertec.spring_mvc_demo.entity.Category;
import edu.pe.cibertec.spring_mvc_demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping
    public String listCategories(Model model){
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("activeCategoriesCount", categories.size());
        model.addAttribute("pageTitle", "Gestion de Categorias");
        return "categories/list";
    }
    @GetMapping("/new")
    public String newCategory(Model model){
        model.addAttribute("category", new Category() );
        model.addAttribute("pageTitle", "Crear nueva categoria");
        return "";
    }
    @PostMapping
    public String createCategory(){
        return "";
    }
    @GetMapping("/{id}/edit")
    public String showEditCategory(){
        return "";
    }
    @PostMapping("/{id}")
    public String editCategory(){
        return  "";
    }
    @GetMapping("/{id}")
    public String viewCategory(){
        return  "";
    }
    @PostMapping("/{id}/delete")
    public String deleteCategory(){
        return  "";
    }

}
