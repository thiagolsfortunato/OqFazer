package br.com.oqfazer.domain.category;

import br.com.oqfazer.domain.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A classe Category representa o modelo de dados de categorias no bd oqfazer.
 *
 * @author Thiago Fortunato
 * @version 1.0
 */

@Data
@Entity
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30, unique = true)
    private String name;

    public Category(final String name) {
        this.name = name;
    }

    public Category(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Category(){}
}
