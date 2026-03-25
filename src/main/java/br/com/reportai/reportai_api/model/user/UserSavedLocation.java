package br.com.reportai.reportai_api.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "user_saved_locations")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSavedLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Dê um nome para este local (ex: Casa, Trabalho)")
    private String label;

    // O PONTO CENTRAL: Onde o usuário marcou no mapa
    @NotNull(message = "A localização geográfica é obrigatória")
    @Column(columnDefinition = "geometry(Point, 4326)", nullable = false)
    private Point location;

    // O RAIO: Até onde ele quer ser avisado (em metros)
    @NotNull(message = "O raio de monitoramento deve ser definido")
    @Min(value = 50, message = "O raio mínimo é de 50 metros")
    @Max(value = 10000, message = "O raio máximo permitido é de 10km")
    @Column(nullable = false)
    private Integer monitoringRadius;

    @Column(nullable = false)
    private boolean notificationsEnabled = true;

    // RELACIONAMENTO: Muitos locais para um Usuário
    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(
            name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_user_saved_locations") // Nome bonitinho aqui
    )

    private User user;
}