package br.com.reportai.reportai_api.model.event;

import br.com.reportai.reportai_api.model.enums.event.EventStatusEnum;
import br.com.reportai.reportai_api.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter

@Entity
@Table(name = "event_history", indexes = {
        @Index(name = "idx_event_history_event_id", columnList = "event_id"),
        @Index(name = "idx_event_history_created_at", columnList = "created_at") // Bônus: acelera a ordenação por data!
})

public class EventHistory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String observation;

    @Enumerated(EnumType.STRING)
    private EventStatusEnum status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(
            name = "event_id",nullable = false,
            foreignKey = @ForeignKey(name = "fk_event_event_history") // Nome bonitinho aqui
    )
    private Event event;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private EventStatusEnum previousStatus; // Para rastrear a origem da mudança

    private String solutionAttachmentUrl; // Foto que prova que o fiscal esteve lá

    @Column(nullable = false)
    private boolean isInternalNote = false;


    @PrePersist
    protected void onCreate (){
        this.createdAt = LocalDateTime.now();
    }

}



