package br.com.oqfazer.api;

import br.com.oqfazer.domain.event.Event;
import br.com.oqfazer.domain.event.impl.EventServiceImpl;
import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    EventServiceImpl service;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/event", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> save(@RequestBody Event event) {
        try {
            Event eventEntity = service.save(event);
            return new ResponseEntity(eventEntity, HttpStatus.CREATED);
        } catch (ExistException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/event", method = RequestMethod.PUT)
    public ResponseEntity<Event> edit(@RequestBody Event event) {
        Event eventEntity = service.edit(event);
        return new ResponseEntity<>(eventEntity, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/event", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Event event) {
        service.delete(event);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USERS')")
    @RequestMapping(path = "/event", method = RequestMethod.GET)
    public ResponseEntity<Event> findByUsername(@RequestParam("name") String name) {
        Event eventEntity = service.findByName(name);
        if (eventEntity != null) return new ResponseEntity<>(eventEntity, HttpStatus.OK);
        else return new ResponseEntity<>(eventEntity, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/events", method = RequestMethod.GET)
    public ResponseEntity<Event> getAll() {
        return new ResponseEntity(service.getAllEvents(), HttpStatus.OK);
    }
}
