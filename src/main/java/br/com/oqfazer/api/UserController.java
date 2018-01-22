package br.com.oqfazer.api;

import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.domain.user.UserService;
import br.com.oqfazer.exception.ExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A classe UserController e reponsavel por disponiblizar os servicos de user
 *
 * @author Thiago Fortunato
 * @version 1.0
 */

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(path = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> save(@RequestBody User user) {
        try {
            User userEntity = service.save(user);
            return new ResponseEntity(userEntity, HttpStatus.CREATED);
        } catch (ExistException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/user", method = RequestMethod.PUT)
    public ResponseEntity<User> edit(@RequestBody User user) {
        User userEntity = service.edit(user);
        return new ResponseEntity(userEntity, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/user", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody User user) {
        service.delete(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/username", method = RequestMethod.GET)
    public ResponseEntity<User> seachByUsername(@RequestParam("username") String username) {
        User userEntity = service.loadUserByUsername(username);
        if (userEntity != null) return new ResponseEntity(userEntity, HttpStatus.OK);
        else return new ResponseEntity(userEntity, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> seachByName(@RequestParam("name") String name) {
        List<User> userList= service.loadUsersByName(name);
        if (userList != null) return new ResponseEntity(userList, HttpStatus.OK);
        else return new ResponseEntity(userList, HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public ResponseEntity<User> getAll() {
        return new ResponseEntity(service.getAllUsers(), HttpStatus.OK);
    }
}
