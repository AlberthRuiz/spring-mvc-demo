package edu.pe.cibertec.spring_mvc_demo.controller;

import edu.pe.cibertec.spring_mvc_demo.entity.Category;
import edu.pe.cibertec.spring_mvc_demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        long activeCategoriesCount = categories.stream().filter(Category::getEstado).count();
        model.addAttribute("categories", categories);
        model.addAttribute("activeCategoriesCount", activeCategoriesCount);
        model.addAttribute("pageTitle", "Gestión de Categorías");
        return "categories/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Crear Nueva Categoría");
        return "categories/form";
    }

    @PostMapping
    public String createCategory(@ModelAttribute Category category, Model model) {
        try {
            categoryService.createCategory(category);
            model.addAttribute("successMessage", "Categoría creada exitosamente");
            return "redirect:/categories";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al crear categoría: " + e.getMessage());
            model.addAttribute("category", category);
            return "categories/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isEmpty()) {
            model.addAttribute("errorMessage", "Categoría no encontrada");
            return "redirect:/categories";
        }
        model.addAttribute("category", category.get());
        model.addAttribute("pageTitle", "Editar Categoría");
        model.addAttribute("isEdit", true);
        return "categories/form";
    }

    @PostMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category, Model model) {
        try {
            categoryService.updateCategory(id, category);
            model.addAttribute("successMessage", "Categoría actualizada exitosamente");
            return "redirect:/categories";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar categoría: " + e.getMessage());
            model.addAttribute("category", category);
            model.addAttribute("isEdit", true);
            return "categories/form";
        }
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isEmpty()) {
            model.addAttribute("errorMessage", "Categoría no encontrada");
            return "redirect:/categories";
        }
        model.addAttribute("category", category.get());
        model.addAttribute("pageTitle", "Detalles de la Categoría");
        return "categories/detail";
    }

    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("successMessage", "Categoría eliminada exitosamente");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Error al eliminar categoría: " + e.getMessage());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error inesperado al eliminar categoría");
        }
        return "redirect:/categories";
    }
}