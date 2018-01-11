package br.com.oqfazer.domain.region;

import br.com.oqfazer.domain.city.City;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "region")
public class Region {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 15)
    private String name;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private List<City> cities;

    public Region() {
    }

    public Region(String name, List<City> cities) {
        this.name = name;
        this.cities = cities;
    }

    public Region(Long id, String name, List<City> cities) {
        this.id = id;
        this.name = name;
        this.cities = cities;
    }

}
