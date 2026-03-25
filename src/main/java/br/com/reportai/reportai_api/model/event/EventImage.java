package br.com.reportai.reportai_api.model.event;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter

@Entity
@Table(name = "event_images") // Padrão da indústria: substantivo principal + o que ele contém
public class EventImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da imagem é obrigatório")
    private String name;

    @NotBlank(message = "A URL da imagem não pode estar em branco")
    @Column(nullable = false)
    private String url;

    private String publicId; // Para deletar do Storage (Cloudinary/S3)

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(
            name = "event_id",nullable = false,
            foreignKey = @ForeignKey(name = "fk_event_event_images") // Nome bonitinho aqui
    )
    // A foto não pode existir sem um evento
    private Event event;

}