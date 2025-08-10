package edu.pe.cibertec.spring_mvc_demo.respository;

import edu.pe.cibertec.spring_mvc_demo.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepostory extends CrudRepository<Category,Long> {

@org.springframework.data.jpa.repository.Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE LOWER(c.nombre) = LOWER(:nombre)")
boolean existsByNombreIgnoreCase(@org.springframework.data.repository.query.Param("nombre") String nombre);

@org.springframework.data.jpa.repository.Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE LOWER(c.nombre) = LOWER(:nombre) AND c.id != :id")
boolean existsByNombreIgnoreCaseAndIdNot(@org.springframework.data.repository.query.Param("nombre") String nombre, @org.springframework.data.repository.query.Param("id") Long id);

}
