package br.com.oqfazer.domain.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository repository;


    public void setRepository(EventRepository repository) {
        this.repository = repository;
    }

    /**
     * O metodo e reponsavel por salvar e editar event
     *
     * @param event
     * @return
     */
    public Event save(final Event event) {
        return repository.save(event);
    }

    /**
     * O metodo e reponsavel por editar event
     *
     * @param event
     * @return
     */
    public Event edit(final Event event) {
        return repository.save(event);
    }

    /**
     * O metodo e reponsavel por deletar event
     *
     * @param event
     */
    public void delete(final Event event) {
        repository.delete(event);
    }

    /**
     * O metodo e reponsavel por buscar todas cities
     *
     * @return List<event>
     */
    public List<Event> getAllCities() {
        return repository.findAll();
    }

    /**
     * O metodo e reponsavel por buscar por nome da event
     *
     * @param name
     * @return
     */
    public List<Event> findByCategory(final String name) {
        return repository.findByCategory(name);
    }

    /**
     * O metodo e reponsavel por buscar por nome da event
     *
     * @param name
     * @return
     */
    public List<Event> findByName(final String name) {
        return repository.findByName(name);
    }

    /**
     * O metodo e reponsavel por buscar por id event
     *
     * @param id
     * @return
     */
    public Event findById(final Long id) {
        return repository.findById(id);
    }
}
