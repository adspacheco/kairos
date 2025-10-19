package town.kairos.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import town.kairos.market.models.MarketModel;

import java.util.Optional;
import java.util.UUID;

public interface MarketService {
    void save(MarketModel marketModel);

    Optional<MarketModel> findById(UUID marketId);

    void delete(MarketModel marketModel);

    Page<MarketModel> findAll(Pageable pageable);
}
