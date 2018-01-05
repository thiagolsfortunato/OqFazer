package br.com.oqfazer.domain.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A classe CityRepository e reponsavel por pelas operacoes de CRUD de city.
 *
 * @author Thiago Fortunato
 * @version 1.0
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    City findByName(String name);

    City findById(Long id);
}
