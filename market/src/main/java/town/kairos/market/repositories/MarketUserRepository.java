package town.kairos.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import town.kairos.market.models.MarketModel;
import town.kairos.market.models.MarketUserModel;

import java.util.UUID;

public interface MarketUserRepository extends JpaRepository<MarketUserModel, UUID> {

    boolean existsByMarketAndUserId(MarketModel marketModel, UUID userId);
}
