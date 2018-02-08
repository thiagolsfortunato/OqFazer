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

    @Column(name = "email", length = 60)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "authorities")
    private String authorities;

    public User() {
        super();
    }

    public User(String name, String username, String password, String email, String phone, String authorities) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.phone = phone;
    }

    public User(Long id, String name, String username, String password, String email, String phone, String authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.phone = phone;
    }
}
