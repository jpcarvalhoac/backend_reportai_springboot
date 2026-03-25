package br.com.reportai.reportai_api.model.event;

import br.com.reportai.reportai_api.model.enums.event.EventCategoryEnum;
import br.com.reportai.reportai_api.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter

@Entity
@Table(name = "events", indexes = {
        @Index(name = "idx_event_category", columnList = "category")
})


public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome obrigatório")
    private String title;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private EventCategoryEnum category;

    // Definição específica para PostGIS: Tipo Ponto, SRID 4326 (GPS Padrão)
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //A lista de imagens do evento
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventImage> images = new ArrayList<>();


    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<CommentEvent> comments = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


}
