package br.com.reportai.reportai_api.repository.user;

import br.com.reportai.reportai_api.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{

    /*Duas opções de login*/
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);

    List<User> findAllOrderBy();

    // Ordena pelo campo 'name' de A a Z
    List<User> findAllByOrderByNameAsc();

    // Ordena pelos mais recentes (assumindo que você tem o campo createdAt)
    List<User> findAllByOrderByCreatedAtDesc();

    /*Verificar se ja existe no banco*/
    boolean existsByEmail(String email);
    boolean existsByCpf(String email);

    @Query(value = "SELECT * FROM events e WHERE " +
            "ST_DWithin(e.location, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326), :distance) " +
            "AND (:category IS NULL OR e.category = :category) " +
            "ORDER BY e.created_at DESC",
            nativeQuery = true)
    List<User> findUsersToNotify(@Param("lat") double lat, @Param("lon") double lon);
}
