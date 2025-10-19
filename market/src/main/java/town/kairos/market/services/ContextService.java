package town.kairos.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import town.kairos.market.models.ContextModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContextService {
    ContextModel save(ContextModel contextModel);
    void delete(ContextModel contextModel);
    Optional<ContextModel> findContextIntoMarket(UUID marketId, UUID contextId);
    List<ContextModel> findAllByMarket(UUID marketId);
    Optional<ContextModel> findById(UUID contextId);
    Page<ContextModel> findAllByMarket(Specification<ContextModel> spec, Pageable pageable);
}
