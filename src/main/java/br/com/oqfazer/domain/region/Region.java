package br.com.oqfazer.domain.region;

import br.com.oqfazer.domain.city.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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

    @OneToMany(mappedBy = "region", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<City> cities;

    public Region() {}

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
