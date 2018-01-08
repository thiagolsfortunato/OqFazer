package br.com.oqfazer.domain.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A classe RegionRepository e reponsavel por pelas operacoes de CRUD de region.
 *
 * @author Thiago Fortunato
 * @version 1.0
 */

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Region findByName(String name);

    Region findById(Long id);
}
