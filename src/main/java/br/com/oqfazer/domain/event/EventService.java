package br.com.oqfazer.domain.event;

import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.exception.ExistException;

import java.util.List;

public interface EventService {

    Event save(Event event) throws ExistException;

    Event edit(Event event);

    void delete(Event event);

    List<Event> getAllEvents();

    Event findById(Long id);

    Event findByName(String name);

    List<Event> findByCity(City city);

    List<Event> findByOwner(User owner);
}
