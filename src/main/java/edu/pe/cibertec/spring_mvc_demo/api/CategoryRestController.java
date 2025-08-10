package edu.pe.cibertec.spring_mvc_demo.api;

import edu.pe.cibertec.spring_mvc_demo.entity.Category;
import edu.pe.cibertec.spring_mvc_demo.service.CategoryService;
import edu.pe.cibertec.spring_mvc_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCatergorires(){
        try{
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
