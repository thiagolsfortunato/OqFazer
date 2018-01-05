package br.com.oqfazer.domain.category;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A classe Category representa o modelo de dados de categorias no bd oqfazer.
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name = "parent")
    private Category parent;


    public Category (final String name, final String description, final Category category) {
        this.name = name;
        this.description = description;
        this.parent = category;
    }
}
