package town.kairos.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import town.kairos.market.models.MarketModel;

import java.util.UUID;

public interface MarketRepository extends JpaRepository<MarketModel, UUID> {
}
