package br.com.oqfazer.domain.event.impl;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.event.Event;
import br.com.oqfazer.domain.event.EventRepository;
import br.com.oqfazer.domain.event.EventService;
import br.com.oqfazer.domain.region.Region;
import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository repository;

    @Override
    public Event save(Event event) throws ExistException {
        Event eventEntity = this.findByName(event.getName());
        if (eventEntity != null ) throw new ExistException();
        else return repository.save(event);
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
    public List<Event> findByCategory(Category category) {
        return repository.findByCategory(category);
    }

    @Override
    public List<Event> findByRegion(Region region) {
        return repository.findByRegion(region);
    }

    @Override
    public List<Event> findByOwner(User owner) {
        return repository.findByOwner(owner);
    }
}
