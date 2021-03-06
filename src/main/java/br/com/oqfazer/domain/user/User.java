package br.com.oqfazer.domain.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A classe User representa o modelo de dados de usuarios no banco monit.
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

    @Column(name = "username", length = 15, nullable = true)
    private String username;

    @Column(name = "password", length = 200, nullable = true)
    private String password;

    @Column(name = "authorities")
    private String authorities;

    @Column(name = "observation")
    private String observation;

    public User() {
        super();
    }

    public User(Long id, String username, String password, String authorities, String observation) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.observation =observation;
    }

    public User(String username, String password, String authorities, String observation) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.observation = observation;
    }

}
