package br.com.oqfazer.domain.event;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.region.Region;
import br.com.oqfazer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A classe EventRepository e reponsavel por pelas operacoes de CRUD de event.
 *
 * @author Thiago Fortunato
 * @version 1.0
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByName(String name);

    List<Event> findByCity(City city);

    List<Event> findByOwner(User owner);

    List<Event> findByParticipants(User participation);

    Event findById(Long id);
}
