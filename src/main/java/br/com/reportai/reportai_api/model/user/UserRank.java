package br.com.reportai.reportai_api.model.user;

import br.com.reportai.reportai_api.model.enums.user.LevelRankUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter

@Entity
@Table(name = "users_rank")
public class UserRank {
    @Id
    Long id;

    @OneToOne
    @MapsId
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_user_user_rank") // Nome bonitinho aqui
    )
    private User user;


    @Column(nullable = false)
    private int totalReports = 0;

    @Column(nullable = false)
    private int resolvedReports = 0;

    @Column(nullable = false)
    private long experiencePoints = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private  LevelRankUser level = LevelRankUser.BRONZE;


    public void addExperience(int amount) {
        this.experiencePoints += amount;
        // Aqui você poderia colocar a lógica de subir de nível:
        // this.level = (int) (experiencePoints / 100) + 1;
    }
}
