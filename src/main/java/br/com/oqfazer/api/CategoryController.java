package br.com.oqfazer.api;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.category.CategoryService;
import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * A classe CategoryController e reponsavel por disponiblizar os servicos de category
 * @author Thiago Fortunato
 * @version 1.0
 */

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/category", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> save(@RequestBody Category user) {
        try {
            Category categoryEntity = service.save(user);
            return new ResponseEntity(categoryEntity, HttpStatus.CREATED);
        } catch (ExistException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/category", method = RequestMethod.PUT)
    public ResponseEntity<Category> edit(@RequestBody Category user) {
        Category userEntity = service.edit(user);
        return new ResponseEntity(userEntity, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Category user) {
        service.delete(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity<Category> findByUsername(@RequestParam("name") String name) {
        Category categoryEntity = service.findByName(name);
        if (categoryEntity != null) return new ResponseEntity(categoryEntity, HttpStatus.OK);
        else return new ResponseEntity(categoryEntity, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/category", method = RequestMethod.GET)
    public ResponseEntity<Category> getAll() {
        return new ResponseEntity(service.getAllCategories(), HttpStatus.OK);
    }

}
