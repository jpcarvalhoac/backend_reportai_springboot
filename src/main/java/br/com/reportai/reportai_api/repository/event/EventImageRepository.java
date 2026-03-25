package br.com.reportai.reportai_api.repository.event;

import br.com.reportai.reportai_api.model.event.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EventImageRepository extends JpaRepository<EventImage, Long> {

    /**
     * DELETAR EM LOTE (Recomendado para performance):
     * Recebe uma lista de IDs [1, 5, 10...] e apaga todos com um único comando SQL.
     * SQL gerado: DELETE FROM event_images WHERE id IN (1, 5, 10);
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM EventImage i WHERE i.id IN :ids")
    void deleteAllByIdIn(@Param("ids") List<Long> ids);

    /**
     * DELETAR TODAS DE UM EVENTO:
     * Útil quando a denúncia inteira é excluída.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM EventImage i WHERE i.event.id = :eventId")
    void deleteAllByEventId(@Param("eventId") Long eventId);

    /**
     * Busca apenas as URLs das imagens de um evento.
     * Isso economiza banda de rede e memória, pois não traz o objeto completo.
     */
    @Query("SELECT i.url FROM EventImage i WHERE i.event.id = :eventId")
    List<String> findAllUrlsByEventId(@Param("eventId") Long eventId);


}
