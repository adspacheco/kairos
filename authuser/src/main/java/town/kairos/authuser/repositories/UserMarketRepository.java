package town.kairos.authuser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import town.kairos.authuser.models.UserMarketModel;
import town.kairos.authuser.models.UserModel;

import java.util.List;
import java.util.UUID;

public interface UserMarketRepository extends JpaRepository<UserMarketModel, UUID> {
    boolean existsByUserAndMarketId(UserModel user, UUID marketId);

    @Query(value="select * from tb_users_markets where user_user_id = :userId", nativeQuery = true)
    List<UserMarketModel> findAllUserMarketIntoUser(@Param("userId") UUID userId);
}
