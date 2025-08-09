package edu.pe.cibertec.spring_mvc_demo.controller;

import edu.pe.cibertec.spring_mvc_demo.entity.Category;
import edu.pe.cibertec.spring_mvc_demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.JMoleculesConverters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
        return "categories/form";
    }
    @PostMapping
    public String createCategory(Model model, @ModelAttribute Category category){
        try{
            categoryService.createCategory(category);
            model.addAttribute("successMessage", "Categoria creada");
            return "redirect:/categories";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error en creacion -> Ex:" +e.getMessage());
            return "categories/form";
        }
    }
    @GetMapping("/{id}/edit")
    public String showEditCategory(@PathVariable Long id, Model model){
        Optional<Category> cat = categoryService.getCategoryById(id);
        if(cat.isEmpty()){
            model.addAttribute("errorMessage", "Categoria no existe");
            return  "redirect:/categories";
        }
        model.addAttribute("category", cat.get());
        model.addAttribute("pageTitle", "Editar Categoria");
        model.addAttribute("isEdit", true);
        return "categories/form";
    }
    @PostMapping("/{id}")
    public String editCategory(@PathVariable Long id, @ModelAttribute Category category, Model model){
        try{
            categoryService.updateCategory(id, category);
            model.addAttribute("successMessage", "Categoria actualizada");
            return "redirect:/categories";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error en editar -> Ex:" +e.getMessage());
            return "categories/form";
        }

    }
    @GetMapping("/{id}")
    public String viewCategory(@PathVariable Long id, Model model ){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isEmpty()){
            model.addAttribute("errorMessage", "Categoria no existe");
            return  "redirect:/categories";
        }
        model.addAttribute("category", category.get());
        model.addAttribute("pageTitle", "Detalle Categoria");
        return  "categories/form";
    }
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id, Model model ){
        try{
            if(categoryService.deleteCategory(id))
                model.addAttribute("successMessage", "Categoria eliminada");
        }catch (Exception e) {
            model.addAttribute("errorMessage", "Error en eliminar -> Ex:" +e.getMessage());
        }
        return "categories/list";
    }

}
