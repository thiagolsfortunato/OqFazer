package br.com.oqfazer.domain.event;

import br.com.oqfazer.domain.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByName(String name);

    List<Event> findByRegion(String name);

    List<Event> findByCategory(String name);

    Event findById(Long id);
}
