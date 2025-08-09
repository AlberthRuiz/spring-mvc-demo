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
    private CategoryRepostory categoryRepostory;

    public Category createCategory (Category category){
        return categoryRepostory.save(category);
    }
    public List<Category> getAllCategories(){
        List<Category> catergories = new ArrayList<>();
        categoryRepostory.findAll().forEach(catergories::add);
        return  catergories;
    }
    public Optional<Category> getCategoryById(Long id){
        return categoryRepostory.findById(id);
    }
    public Category updateCategory(Long id, Category category){
        Optional<Category> isExists = categoryRepostory.findById(id);
        if (isExists.isEmpty()){
            throw  new IllegalArgumentException("Producto no encontrado");
        }
        Category categoryUpdate = isExists.get();
        if(category.getNombre()!= null && !category.getNombre().trim().isEmpty()){
            categoryUpdate.setNombre(category.getNombre());
        }
        return  categoryRepostory.save(categoryUpdate);
    }

    public boolean deleteCategory(Long id){
        categoryRepostory.deleteById(id);
        return true ;
    }

}
