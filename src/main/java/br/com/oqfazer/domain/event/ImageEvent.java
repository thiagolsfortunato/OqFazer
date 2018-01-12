package br.com.oqfazer.domain.event;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "image_event")
public class ImageEvent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private byte[] image;

    @Column(name = "file_name", length = 50)
    private String fileName;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(name = "event_id")
    private Event event;

    public ImageEvent() {}

    public ImageEvent(Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public ImageEvent(String fileName) {
        this.fileName = fileName;
    }

}
