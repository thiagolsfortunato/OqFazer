package br.com.oqfazer.domain.event.impl;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.category.CategoryService;
import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.event.Event;
import br.com.oqfazer.domain.event.EventRepository;
import br.com.oqfazer.domain.event.EventService;
import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Event save(Event event) throws ExistException {
        Event eventEntity = this.findByName(event.getName());
        if (eventEntity != null ) throw new ExistException();
        else {
            for (Category cat : event.getCategories()) {
                if (cat.getId() == null) categoryService.save(cat);
            }
            eventEntity = repository.save(event);
            return eventEntity;
        }
    }

    @Override
    public Event edit(Event event) {
        return repository.save(event);
    }

    @Override
    public void delete(Event event) {
        repository.delete(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    @Override
    public Event findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Event findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Event> findByCity(City city) {
        return repository.findByCity(city);
    }

    @Override
    public List<Event> findByOwner(User owner) {
        return repository.findByOwner(owner);
    }
}
