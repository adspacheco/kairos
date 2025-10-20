package town.kairos.authuser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import town.kairos.authuser.models.UserMarketModel;
import town.kairos.authuser.models.UserModel;

import java.util.UUID;

public interface UserMarketRepository extends JpaRepository<UserMarketModel, UUID> {
    boolean existsByUserAndMarketId(UserModel user, UUID marketId);
}
