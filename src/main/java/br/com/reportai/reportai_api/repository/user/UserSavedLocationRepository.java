package br.com.reportai.reportai_api.repository.user;

import br.com.reportai.reportai_api.model.user.UserSavedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSavedLocationRepository extends JpaRepository<UserSavedLocation, Long> {

    /**
     * Busca todos os locais salvos de um usuário específico.
     * Útil para listar "Meus Lugares Monitorados" no app.
     */
    List<UserSavedLocation> findByUserId(Long userId);

    /**
     * A QUERY DO ALERTA: Encontra todos os locais salvos que "cobrem" um ponto específico.
     * * Imagine que alguém cadastrou um buraco na Lat/Lon informada.
     * Esta query verifica se esse ponto está dentro do 'monitoring_radius' de algum local salvo.
     */
    @Query(value = "SELECT * FROM user_saved_locations usl WHERE " +
            "usl.notifications_enabled = true AND " +
            "ST_DWithin(usl.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), usl.monitoring_radius)",
            nativeQuery = true)
    List<UserSavedLocation> findLocationsToNotify(@Param("lat") double lat, @Param("lon") double lon);
}