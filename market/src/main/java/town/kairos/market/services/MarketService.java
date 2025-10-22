package town.kairos.market.services;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import town.kairos.market.models.MarketModel;

import java.util.Optional;
import java.util.UUID;

public interface MarketService {
    MarketModel save(MarketModel marketModel);

    void delete(MarketModel marketModel);

    Optional<MarketModel> findById(UUID marketId);

    Page<MarketModel> findAll(Specification<MarketModel> spec, Pageable pageable);

    boolean existsByMarketAndUser(UUID marketId, @NotNull UUID userId);

    void saveSubscriptionUserInMarket(UUID marketId, UUID userId);
}
