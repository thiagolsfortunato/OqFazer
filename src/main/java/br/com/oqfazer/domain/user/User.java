package br.com.oqfazer.domain.user;

import br.com.oqfazer.domain.event.Event;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * A classe User representa o modelo de dados de usuarios no banco oqfazer.
 * @author Thiago Fortunato
 * @version 1.0
 */
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 2353528370345499815L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, nullable = true)
    private String name;

    @Column(name = "username", length = 20, nullable = true)
    private String username;

    @Column(name = "password", length = 200, nullable = true)
    private String password;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "observation")
    private String observation;

    @ManyToMany(mappedBy ="participants", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Event> participation;

    public User() {
        super();
    }

    public User(String name, String username, String password, String authorities, String observation) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.observation = observation;
    }

    public User(Long id, String name, String username, String password, String authorities, String observation) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.observation = observation;
    }

    public User(String name, String username, String password, String authorities, String observation, List<Event> participation) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.observation = observation;
        this.participation = participation;
    }

    public User(Long id, String name, String username, String password, String authorities, String observation, List<Event> participation) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.observation =observation;
        this.participation = participation;
    }

}
