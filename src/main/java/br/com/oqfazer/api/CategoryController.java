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

import java.util.List;

/**
 * A classe CategoryController e reponsavel por disponiblizar os servicos de category
 *
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
    public ResponseEntity<Category> save(@RequestBody Category category) {
        Category categoryEntity = null;
        try {
            categoryEntity = service.save(category);
            return new ResponseEntity(categoryEntity, HttpStatus.CREATED);
        } catch (ExistException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/category", method = RequestMethod.PUT)
    public ResponseEntity<Category> edit(@RequestBody Category category) {
        Category categoryEntity = service.edit(category);
        return new ResponseEntity<>(categoryEntity, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/category", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Category category) {
        service.delete(category);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USERS')")
    @RequestMapping(path = "/category", method = RequestMethod.GET)
    public ResponseEntity<Category> findByName(@RequestParam("name") String name) {
        return new ResponseEntity(service.findByName(name), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USERS')")
    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public ResponseEntity<Category> getAll() { return new ResponseEntity(service.getAllCategories(), HttpStatus.OK); }
}
