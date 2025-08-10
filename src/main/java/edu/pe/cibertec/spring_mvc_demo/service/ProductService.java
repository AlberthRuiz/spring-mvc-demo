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

    @Autowired
    private CategoryService categoryService;

    public Product createProduct(Product product) {
        if (product.getNombre() == null || product.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (product.getPrecio() == null || product.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        if (!categoryService.existsById(product.getCategory().getId())) {
            throw new IllegalArgumentException("La categoría especificada no existe");
        }

        product.setEstado(product.getStock() > 0);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }

        Product product = existingProduct.get();

        if (productDetails.getNombre() != null && !productDetails.getNombre().trim().isEmpty()) {
            product.setNombre(productDetails.getNombre());
        }

        if (productDetails.getPrecio() != null && productDetails.getPrecio().compareTo(BigDecimal.ZERO) > 0) {
            product.setPrecio(productDetails.getPrecio());
        }

        if (productDetails.getStock() != null && productDetails.getStock() >= 0) {
            product.setStock(productDetails.getStock());
            product.setEstado(productDetails.getStock() > 0);
        }

        if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
            if (!categoryService.existsById(productDetails.getCategory().getId())) {
                throw new IllegalArgumentException("La categoría especificada no existe");
            }
            product.setCategory(productDetails.getCategory());
        }

        return productRepository.save(product);
    }

    public boolean deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(Long id) {
        return id != null && id > 0 && productRepository.existsById(id);
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("ID de categoría inválido");
        }
        
        List<Product> allProducts = getAllProducts();
        return allProducts.stream()
                .filter(product -> product.getCategory() != null && 
                                 categoryId.equals(product.getCategory().getId()))
                .toList();
    }
}