package br.com.reportai.reportai_api.model.event;

import br.com.reportai.reportai_api.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommentEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O conteúdo do comentário não pode estar vazio")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private int likes = 0;
    private int dislikes = 0;

    // Relacionamento com o Usuário que comentou
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_user"))
    private User user;

    // Relacionamento com o Evento (Denúncia)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_event"))
    private Event event;

    // --- AUTO-RELACIONAMENTO (PARA RESPOSTAS) ---

    // O comentário "pai" (se for nulo, é um comentário principal)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_comment_parent"))
    private CommentEvent parent;

    // Lista de respostas que este comentário recebeu
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt ASC")
    private List<CommentEvent> replies = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}