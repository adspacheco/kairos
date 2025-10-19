package town.kairos.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import town.kairos.market.models.MarketModel;
import town.kairos.market.repositories.MarketRepository;
import town.kairos.market.services.MarketService;

import java.util.Optional;
import java.util.UUID;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketRepository marketRepository;

    @Override
    public void save(MarketModel marketModel) {
        marketRepository.save(marketModel);
    }

    @Override
    public Optional<MarketModel> findById(UUID marketId) {
        return marketRepository.findById(marketId);
    }

    @Override
    public void delete(MarketModel marketModel) {
        marketRepository.delete(marketModel);
    }

    @Override
    public Page<MarketModel> findAll(Pageable pageable) {
        return marketRepository.findAll(pageable);
    }
}
