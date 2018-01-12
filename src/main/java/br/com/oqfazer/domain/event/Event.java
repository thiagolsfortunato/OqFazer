package br.com.oqfazer.domain.event;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.region.Region;
import br.com.oqfazer.domain.user.User;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "event")
public class Event implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50)
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

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(targetEntity = Region.class)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "participants")
    private Set<User> participants;

    private Event() {}

    public Event(String name, String description, Calendar event_date, String local, String imageUrl, Category category, Region region, User owner) {
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.category = category;
        this.region = region;
        this.owner = owner;
    }

    public Event(Long id, String name, String description, Calendar event_date, String local, String imageUrl, Category category, Region region, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.category = category;
        this.region = region;
        this.owner = owner;
    }

    public Event(String name, String description, Calendar event_date, String local, String imageUrl, Region region, User owner, Set<User> participants) {
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.region = region;
        this.owner = owner;
        this.participants = participants;
    }

    public Event(Long id, String name, String description, Calendar event_date, String local, String imageUrl, Region region, User owner, Set<User> participants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.region = region;
        this.owner = owner;
        this.participants = participants;
    }
}
