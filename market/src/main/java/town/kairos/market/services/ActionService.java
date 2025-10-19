package town.kairos.market.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import town.kairos.market.models.ActionModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActionService {
    ActionModel save(ActionModel actionModel);
    void delete(ActionModel actionModel);
    Optional<ActionModel> findActionIntoContext(UUID contextId, UUID actionId);
    List<ActionModel> findAllByContext(UUID contextId);
    Optional<ActionModel> findById(UUID actionId);
    Page<ActionModel> findAllByContext(Specification<ActionModel> spec, Pageable pageable);
}
