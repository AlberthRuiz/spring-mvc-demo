package edu.pe.cibertec.spring_mvc_demo.respository;

import edu.pe.cibertec.spring_mvc_demo.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
