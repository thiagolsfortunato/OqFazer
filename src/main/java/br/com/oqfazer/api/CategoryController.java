package br.com.oqfazer.api;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        Category categoryEntity = service.save(category);
        return new ResponseEntity(categoryEntity, HttpStatus.CREATED);
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
    public ResponseEntity<Category> findByUsername(@RequestParam("name") String name) {
        Category categoryEntity = service.findByName(name);
        if (categoryEntity != null) return new ResponseEntity<>(categoryEntity, HttpStatus.OK);
        else return new ResponseEntity<>(categoryEntity, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/categories", method = RequestMethod.GET)
    public ResponseEntity<Category> getAll() {
        return new ResponseEntity(service.getAllCategories(), HttpStatus.OK);
    }

}
