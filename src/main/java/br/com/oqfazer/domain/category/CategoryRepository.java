package br.com.oqfazer.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A classe CategoryRepository e reponsavel por pelas operacoes de CRUD de category.
 *
 * @author Thiago Fortunato
 * @version 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    Category findById(Long id);
}
