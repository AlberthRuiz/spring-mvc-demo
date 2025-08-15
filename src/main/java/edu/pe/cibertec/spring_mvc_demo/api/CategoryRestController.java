package edu.pe.cibertec.spring_mvc_demo.api;

import edu.pe.cibertec.spring_mvc_demo.entity.Category;
import edu.pe.cibertec.spring_mvc_demo.service.CategoryService;
import edu.pe.cibertec.spring_mvc_demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id){
        try {
            Optional<Category> category = categoryService.getCategoryById(id);
            if(category.isPresent()){
                return  ResponseEntity.ok(category.get());
            }else {
                return  ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();

        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        try {
            Category savedCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();

        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category){
        try {
            Category updateddCategory = categoryService.updateCategory(id,category);
            return ResponseEntity.ok(updateddCategory);
        } catch (RuntimeException e) {
            if(e.getMessage().contains("no encontrada")){
                return  ResponseEntity.notFound().build();
            }else {
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
        try {
            boolean deleted = categoryService.deleteCategory(id);
            if(deleted){
                return  ResponseEntity.noContent().build();
            }else {
                return  ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            if(e.getMessage().contains("productos asociados")){
                return  ResponseEntity.status(HttpStatus.CONFLICT).build();
            }else {
                return ResponseEntity.badRequest().build();
            }

        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> getActiveCategories(){
        try{
            List<Category> categories = categoryService.getActiveCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
