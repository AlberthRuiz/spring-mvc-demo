package edu.pe.cibertec.spring_mvc_demo.service;

import edu.pe.cibertec.spring_mvc_demo.entity.Product;
import edu.pe.cibertec.spring_mvc_demo.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){
        if(product.getNombre() == null || product.getNombre().trim().isEmpty()){
            throw  new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if(product.getPrecio() == null || product.getPrecio().compareTo(BigDecimal.ZERO) <=0){
            throw  new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if(product.getStock() == null || product.getStock() <=0){
            throw  new IllegalArgumentException("El stock debe ser mayor a 0");
        }
        return  productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Optional<Product> getProductById(Long id){
        return  productRepository.findById(id);
    }
    public Product updateProduct(Long id, Product product){
        Optional<Product> objProduct = productRepository.findById(id);
        if (objProduct== null){
            throw  new IllegalArgumentException("Producto no encontrado");
        }
        Product update_product = objProduct.get();
        if(product.getNombre() != null || !product.getNombre().trim().isEmpty()){
            update_product.setNombre(product.getNombre());
        }
        if(product.getPrecio() != null || product.getPrecio().compareTo(BigDecimal.ZERO) >=0){
            update_product.setPrecio(product.getPrecio());
        }
        if(product.getStock() != null || product.getStock() >=0){
            update_product.setStock(product.getStock());
        }
        if(product.getCategoria() != null || !product.getCategoria().trim().isEmpty()){
            update_product.setCategoria(product.getCategoria());
        }
        update_product.setEstado(update_product.isAvailable());
        return productRepository.save(update_product);
    }

    public boolean deleteProduct(Long id){
        if(id == null || id<=0){
            throw  new IllegalArgumentException("ID no valido");
        }
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
            return  true;
        }
        return false;
    }

    public List<String> getAllCategories() {
        return productRepository.findAllCategories();
    }
}
