package br.com.reportai.reportai_api.repository.event;

import br.com.reportai.reportai_api.model.event.EventHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Optional;

@Repository
public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {

    /**
     * Lista TODO o histórico do sistema de forma segura.
     * Uso de Pageable evita carregar milhares de registros de uma vez na RAM.
     * Ideal para o Dashboard do Administrador/Prefeitura.
     */
    Page<EventHistory> findAll(  Pageable pageable);

    /**
     * Busca a "Linha do Tempo" de um evento específico.
     * Retorna todos os registros vinculados ao Event ID, ordenados do mais recente.
     */
    List<EventHistory> findByEventIdOrderByCreatedAtDesc(Long eventId);

    /**
     * Busca apenas a última atualização de um evento.
     * Usado para mostrar o status atual no mapa sem processar o histórico todo.
     */
    Optional<EventHistory> findFirstByEventIdOrderByCreatedAtDesc(Long eventId);

    /**
     * Busca ações realizadas por um usuário (Fiscal/Admin).
     * Essencial para auditoria de desempenho dos servidores públicos.
     */
    List<EventHistory> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Deleta todos os históricos de um evento em uma única transação.
     * @Modifying + @Query evita o problema de performance N+1 (delete um por um).
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM EventHistory e WHERE e.event.id = :eventId")
    void deleteAllByEventId(@Param("eventId") Long eventId);
}