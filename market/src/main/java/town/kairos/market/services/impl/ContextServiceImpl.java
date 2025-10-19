package town.kairos.market.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import town.kairos.market.models.ActionModel;
import town.kairos.market.models.ContextModel;
import town.kairos.market.repositories.ActionRepository;
import town.kairos.market.repositories.ContextRepository;
import town.kairos.market.services.ContextService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContextServiceImpl implements ContextService {

    @Autowired
    ContextRepository contextRepository;

    @Autowired
    ActionRepository actionRepository;

    @Override
    public ContextModel save(ContextModel contextModel) {
        return contextRepository.save(contextModel);
    }

    @Transactional
    @Override
    public void delete(ContextModel contextModel) {
        List<ActionModel> actions = actionRepository.findAllActionsIntoContext(contextModel.getContextId());
        if (!actions.isEmpty()) {
            actionRepository.deleteAll(actions);
        }
        contextRepository.delete(contextModel);
    }

    @Override
    public Optional<ContextModel> findContextIntoMarket(UUID marketId, UUID contextId) {
        return contextRepository.findContextIntoMarket(marketId, contextId);
    }

    @Override
    public List<ContextModel> findAllByMarket(UUID marketId) {
        return contextRepository.findAllContextsIntoMarket(marketId);
    }

    @Override
    public Optional<ContextModel> findById(UUID contextId) {
        return contextRepository.findById(contextId);
    }

    @Override
    public Page<ContextModel> findAllByMarket(Specification<ContextModel> spec, Pageable pageable) {
        return contextRepository.findAll(spec, pageable);
    }
}
