package br.com.reportai.reportai_api.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter

@Entity
@Table (name = "user_preferences")

public class UserPreferences {

    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_user_preferences") // Nome bonitinho aqui
    )
    private User user;

    @Column(nullable = false)
    private boolean notifyNearbyEvents = true;

    @NotBlank
    private String theme = "LIGHT";

    @Column(nullable = false)
    private double notificationRadius = 500.0; // A "Distance" em metros do raio (ex: 500m)




}
