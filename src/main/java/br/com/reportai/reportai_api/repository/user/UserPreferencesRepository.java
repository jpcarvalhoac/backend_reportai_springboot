package br.com.reportai.reportai_api.repository.user;

import br.com.reportai.reportai_api.model.user.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {



    /**
     * Busca as configurações de um usuário específico.
     * Como usamos @MapsId, o ID da preferência é o mesmo ID do usuário.
     */
    Optional<UserPreferences> findByUserId(Long userId);

    /**
     * Busca todos os usuários que ATIVARAM as notificações próximas.
     * Útil se você optar por processar a lista no Java em vez de um JOIN complexo.
     */
      //java.util.List<UserPreferences> findByNotifyNearbyEventsTrue();

}
