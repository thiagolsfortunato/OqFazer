package br.com.oqfazer.domain.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "description", length = 50)
    private String description;

    @JsonIgnore
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "parent")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> sons = new LinkedList<>();

    public Category(final String name, final String description, final Category parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

    public Category(final Long id, final String name, final String description, final Category parent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

    public Category(){}
}
