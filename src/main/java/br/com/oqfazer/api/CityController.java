package br.com.oqfazer.api;

import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    private CityService service;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/city", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<City> save(@RequestBody City city) {
        City cityEntity = service.save(city);
        return new ResponseEntity(cityEntity, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/city", method = RequestMethod.PUT)
    public ResponseEntity<City> edit(@RequestBody City city) {
        City cityEntity = service.edit(city);
        return new ResponseEntity<>(cityEntity, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/city", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody City city) {
        service.delete(city);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USERS')")
    @RequestMapping(path = "/city", method = RequestMethod.GET)
    public ResponseEntity<City> findByUsername(@RequestParam("name") String name) {
        City cityEntity = service.findByName(name);
        if (cityEntity != null) return new ResponseEntity<>(cityEntity, HttpStatus.OK);
        else return new ResponseEntity<>(cityEntity, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/cities", method = RequestMethod.GET)
    public ResponseEntity<City> getAll() {
        return new ResponseEntity(service.getAllCities(), HttpStatus.OK);
    }
}
