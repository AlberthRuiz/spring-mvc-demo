package edu.pe.cibertec.spring_mvc_demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    // Relación Many-to-One con Category
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Category category;

    @Column(name = "estado", nullable = false)
    private Boolean estado; // true = disponible, false = agotado

    // Constructor vacío (requerido por JPA)
    public Product() {}

    // Constructor con parámetros
    public Product(String nombre, BigDecimal precio, Integer stock, Category category, Boolean estado) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.category = category;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    // Método de utilidad para verificar si está disponible
    public boolean isDisponible() {
        return estado && stock > 0;
    }

    // toString para depuración
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", category=" + (category != null ? category.getNombre() : "null") +
                ", estado=" + estado +
                '}';
    }
}