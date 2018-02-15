package br.com.oqfazer.domain.event;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.city.City;
import br.com.oqfazer.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

@Data
@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 70)
    private String name;

    @Column(name = "description", length = 70)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_date", nullable = false)
    private Calendar event_date;

    @Column(name = "local", length = 50)
    private String local;

    @Column(name = "imageUrl", length = 70)
    private String imageUrl;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "categories", nullable = false)
    private Set<Category> categories;

    @ManyToOne(targetEntity = City.class)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "participants")
    private Set<User> participants;

    private Event() {
    }

    public Event(String name, String description, Calendar event_date, String local, String imageUrl, Set<Category> categories, City city, User owner) {
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.city = city;
        this.owner = owner;
    }

    public Event(Long id, String name, String description, Calendar event_date, String local, String imageUrl, Set<Category> categories, City city, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.city = city;
        this.owner = owner;
    }

    public Event(String name, String description, Calendar event_date, String local, String imageUrl, Set<Category> categories, City city, User owner, Set<User> participants) {
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.city = city;
        this.owner = owner;
        this.participants = participants;
    }

    public Event(Long id, String name, String description, Calendar event_date, String local, String imageUrl, Set<Category> categories, City city, User owner, Set<User> participants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.city = city;
        this.owner = owner;
        this.participants = participants;
    }
}
