package edu.pe.cibertec.spring_mvc_demo.service;

import edu.pe.cibertec.spring_mvc_demo.entity.Category;
import edu.pe.cibertec.spring_mvc_demo.respository.CategoryRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepostory categoryRepository;

    public Category createCategory(Category category) {
        if (category.getNombre() == null || category.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }

        if (existsByName(category.getNombre().trim())) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + category.getNombre());
        }

        category.setNombre(category.getNombre().trim());

        if (category.getDescripcion() != null) {
            category.setDescripcion(category.getDescripcion().trim());
        }

        category.setEstado(true);

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public List<Category> getActiveCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            if (category.getEstado()) {
                categories.add(category);
            }
        });
        return categories;
    }

    public Optional<Category> getCategoryById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return Optional.empty();
        }
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories.stream()
                .filter(category -> category.getNombre().equalsIgnoreCase(nombre.trim()))
                .findFirst();
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Optional<Category> existingCategory = categoryRepository.findById(id);

        if (existingCategory.isEmpty()) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }

        Category category = existingCategory.get();

        if (categoryDetails.getNombre() != null && !categoryDetails.getNombre().trim().isEmpty()) {
            String newName = categoryDetails.getNombre().trim();
            // Verificar si el nuevo nombre ya existe en otra categoría
            if (!newName.equalsIgnoreCase(category.getNombre()) && existsByName(newName)) {
                throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + newName);
            }
            category.setNombre(newName);
        }

        if (categoryDetails.getDescripcion() != null) {
            category.setDescripcion(categoryDetails.getDescripcion().trim());
        }

        category.setEstado(categoryDetails.getEstado());

        return categoryRepository.save(category);
    }

    public boolean deleteCategory(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        if (!categoryRepository.existsById(id)) {
            return false;
        }

        // Verificar si la categoría tiene productos asociados
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent() && !category.get().getProducts().isEmpty()) {
            throw new RuntimeException("No se puede eliminar la categoría porque tiene productos asociados");
        }

        categoryRepository.deleteById(id);
        return true;
    }

    public boolean existsById(Long id) {
        return id != null && id > 0 && categoryRepository.existsById(id);
    }

    public boolean existsByName(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return categoryRepository.existsByNombreIgnoreCase(nombre.trim());
    }

    public List<Category> searchCategoriesByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllCategories();
        }
        
        List<Category> allCategories = getAllCategories();
        return allCategories.stream()
                .filter(category -> category.getNombre().toLowerCase().contains(searchTerm.toLowerCase().trim()))
                .toList();
    }

    public Category toggleCategoryStatus(Long id) {
        Optional<Category> categoryOpt = getCategoryById(id);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        
        Category category = categoryOpt.get();
        category.setEstado(!category.getEstado());
        return categoryRepository.save(category);
    }
}