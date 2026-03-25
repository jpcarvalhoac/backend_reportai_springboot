package br.com.reportai.reportai_api.repository.event;

import br.com.reportai.reportai_api.model.enums.event.EventCategoryEnum;
import br.com.reportai.reportai_api.model.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface de acesso a dados para a entidade Event.
 * Utiliza Spring Data JPA para consultas automáticas e PostGIS para consultas espaciais.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Busca todas as denúncias de um usuário específico.
     * Útil para a tela "Minhas Publicações" do app.
     */
    List<Event> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Busca denúncias por categoria com suporte a paginação.
     * Ideal para feeds de notícias ou listas longas filtradas por tipo.
     */
    Page<Event> findByCategoryOrderByCreatedAtDesc(EventCategoryEnum category, Pageable pageable);

    /**
     * Busca denúncias por categoria sem paginação (lista completa).
     */
    List<Event> findByCategoryOrderByCreatedAtDesc(EventCategoryEnum category);

    /**
     * RADAR GEOGRÁFICO: Busca eventos por proximidade com filtro de categoria opcional.
     * Utiliza ST_DWithin para performance máxima via índice GIST no PostGIS.
     * Se 'category' for null, a query ignora o filtro de categoria e traz tudo no raio.
     */
    @Query(value = "SELECT * FROM events e WHERE " +
            "ST_DWithin(e.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distance) " +
            "AND (:category IS NULL OR e.category = :category) " +
            "ORDER BY e.created_at DESC",
            nativeQuery = true)
    List<Event> findNearbyEventsOptionalCategory(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("distance") double distance,
            @Param("category") String category
    );

    /**
     * BUSCA ESPECIALIZADA: Proximidade + Categoria Obrigatória com Paginação.
     * Inclui 'countQuery' manual porque o Spring Data JPA não consegue gerar a
     * contagem automática de páginas em consultas nativas complexas com PostGIS.
     */
    @Query(value = "SELECT * FROM events e WHERE ST_DWithin(e.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :radius) " +
            "AND e.category = :#{#category.name()} ORDER BY e.created_at DESC",
            countQuery = "SELECT count(*) FROM events e WHERE ST_DWithin(e.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :radius) " +
                    "AND e.category = :#{#category.name()}",
            nativeQuery = true)
    Page<Event> findByLocationAndCategory(
            @Param("lon") double lon,
            @Param("lat") double lat,
            @Param("radius") double radius,
            @Param("category") EventCategoryEnum category,
            Pageable pageable
    );
}