package edu.pe.cibertec.spring_mvc_demo.service;

import edu.pe.cibertec.spring_mvc_demo.entity.Product;
import edu.pe.cibertec.spring_mvc_demo.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

}
