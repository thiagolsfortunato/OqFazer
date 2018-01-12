package br.com.oqfazer.domain.city;

import br.com.oqfazer.domain.region.Region;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 70)
    private String name;

    @ManyToOne(targetEntity = Region.class)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    public City() {}

    public City(String name, Region region) {
        this.name = name;
        this.region = region;
    }

    public City(Long id, String name, Region region) {
        this.id = id;
        this.name = name;
        this.region = region;
    }
}
