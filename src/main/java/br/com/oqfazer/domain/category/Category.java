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

    @Column(name = "username", length = 15, nullable = true)
    private String name;

    @Column(name = "description", length = 50, nullable = true)
    private String description;

    public Category (final String name, final String description) {
        this.name = name;
        this.description = description;
    }
}
