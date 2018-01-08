package br.com.oqfazer.api;

import br.com.oqfazer.domain.region.Region;
import br.com.oqfazer.domain.region.RegionService;
import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegionController {

    @Autowired
    RegionService service;


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/region", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Region> save(@RequestBody Region region) {
        try {
            Region regionEntity = service.save(region);
            return new ResponseEntity(regionEntity, HttpStatus.CREATED);
        } catch (ExistException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/region", method = RequestMethod.PUT)
    public ResponseEntity<Region> edit(@RequestBody Region region) {
        Region regionEntity = service.edit(region);
        return new ResponseEntity<>(regionEntity, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/region", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Region region) {
        service.delete(region);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USERS')")
    @RequestMapping(path = "/region", method = RequestMethod.GET)
    public ResponseEntity<Region> findByUsername(@RequestParam("name") String name) {
        Region regionEntity = service.findByName(name);
        if (regionEntity != null) return new ResponseEntity<>(regionEntity, HttpStatus.OK);
        else return new ResponseEntity<>(regionEntity, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/regions", method = RequestMethod.GET)
    public ResponseEntity<Region> getAll() {
        return new ResponseEntity(service.getAllRegions(), HttpStatus.OK);
    }

}
