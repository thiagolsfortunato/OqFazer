package br.com.oqfazer.domain.event;

import br.com.oqfazer.domain.category.Category;
import br.com.oqfazer.domain.region.Region;
import br.com.oqfazer.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Data
@Entity
@Table(name = "event")
public class Event {

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "participants")
    private List<User> participants;

    private Event() {}

    public Event(String name, String description, Calendar event_date, String local, String imageUrl, Region region, User owner, List<User> participants) {
        this.name = name;
        this.description = description;
        this.event_date = event_date;
        this.local = local;
        this.imageUrl = imageUrl;
        this.region = region;
        this.owner = owner;
        this.participants = participants;
    }

    public Event(Long id, String name, String description, Calendar event_date, String local, String imageUrl, Region region, User owner, List<User> participants) {
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
