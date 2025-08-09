package edu.pe.cibertec.spring_mvc_demo.respository;

import edu.pe.cibertec.spring_mvc_demo.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepostory extends CrudRepository<Category,Long> {

}
