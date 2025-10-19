package town.kairos.market.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import town.kairos.market.models.ActionModel;
import town.kairos.market.repositories.ActionRepository;
import town.kairos.market.services.ActionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    ActionRepository actionRepository;

    @Transactional
    @Override
    public void delete(ActionModel actionModel) {
        actionRepository.delete(actionModel);
    }

    @Override
    public ActionModel save(ActionModel actionModel) {
        return actionRepository.save(actionModel);
    }

    @Override
    public Optional<ActionModel> findActionIntoContext(UUID contextId, UUID actionId) {
        return actionRepository.findActionIntoContext(contextId, actionId);
    }

    @Override
    public List<ActionModel> findAllByContext(UUID contextId) {
        return actionRepository.findAllActionsIntoContext(contextId);
    }

    @Override
    public Optional<ActionModel> findById(UUID actionId) {
        return actionRepository.findById(actionId);
    }

    @Override
    public Page<ActionModel> findAllByContext(Specification<ActionModel> spec, Pageable pageable) {
        return actionRepository.findAll(spec, pageable);
    }
}
