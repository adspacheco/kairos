package town.kairos.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import town.kairos.market.models.ActionModel;
import town.kairos.market.models.ContextModel;
import town.kairos.market.models.MarketModel;
import town.kairos.market.repositories.ActionRepository;
import town.kairos.market.repositories.ContextRepository;
import town.kairos.market.repositories.MarketRepository;
import town.kairos.market.repositories.UserRepository;
import town.kairos.market.services.MarketService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketRepository marketRepository;
    @Autowired
    private ContextRepository contextRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private UserRepository marketUserRepository;

    @Override
    public MarketModel save(MarketModel marketModel) {
        return marketRepository.save(marketModel);
    }

    @Transactional
    @Override
    public void delete(MarketModel marketModel) {
        List<ContextModel> contextModelList = contextRepository.findAllContextsIntoMarket(marketModel.getMarketId());
        if (!contextModelList.isEmpty()){
            for(ContextModel context : contextModelList){
                List<ActionModel> actionModelList = actionRepository.findAllActionsIntoContext(context.getContextId());
                if (!actionModelList.isEmpty()){
                    actionRepository.deleteAll(actionModelList);
                }
            }
            contextRepository.deleteAll(contextModelList);
        }
        marketRepository.delete(marketModel);
    }

    @Override
    public Optional<MarketModel> findById(UUID marketId) {
        return marketRepository.findById(marketId);
    }

    @Override
    public Page<MarketModel> findAll(Specification<MarketModel> spec, Pageable pageable) {
        return marketRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByMarketAndUser(UUID marketId, UUID userId) {
        return marketRepository.existsByMarketAndUser(marketId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInMarket(UUID marketId, UUID userId) {
        marketRepository.saveMarketUser(marketId, userId);
    }
}
