package br.com.reportai.reportai_api.repository.user;
import br.com.reportai.reportai_api.model.enums.user.LevelRankUser;
import br.com.reportai.reportai_api.model.user.UserRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRankRepository extends JpaRepository<UserRank, Long> {

    /**
     * Busca o ranking de um usuário específico.
     * Como cada usuário tem apenas um rank (1:1), usamos o MapsId novamente.
     */
    Optional<UserRank> findByUserId(Long userId);

    /**
     * TOP 10 Cidadãos: Busca os usuários com maior pontuação.
     * Ideal para criar um "Leaderboard" e incentivar a participação em Ponte Nova.
     */
    List<UserRank> findTop10ByOrderByExperiencePointsDesc();

    /**
     * Busca usuários por nível (ex: Bronze, Prata, Ouro).
     */
    List<UserRank> findByLevel(LevelRankUser level);
}