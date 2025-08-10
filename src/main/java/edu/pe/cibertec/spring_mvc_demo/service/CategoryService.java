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
        throw new UnsupportedOperationException("Implementar findByEstadoTrueOrderByNombreAsc en CategoryRepostory");
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
        throw new UnsupportedOperationException("Implementar findByNombre en CategoryRepostory");
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Optional<Category> existingCategory = categoryRepository.findById(id);

        if (existingCategory.isEmpty()) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }

        Category category = existingCategory.get();

        if (categoryDetails.getNombre() != null && !categoryDetails.getNombre().trim().isEmpty()) {
            String newName = categoryDetails.getNombre().trim();
            throw new UnsupportedOperationException("Implementar existsByNombreIgnoreCaseAndIdNot en CategoryRepostory");
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

        throw new UnsupportedOperationException("Implementar countProductsByCategory en CategoryRepostory");
    }

    public boolean existsById(Long id) {
        return id != null && id > 0 && categoryRepository.existsById(id);
    }

    public boolean existsByName(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        throw new UnsupportedOperationException("Implementar existsByNombreIgnoreCase en CategoryRepostory");
    }
}