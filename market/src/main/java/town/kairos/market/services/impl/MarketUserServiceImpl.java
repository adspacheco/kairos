package town.kairos.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import town.kairos.market.clients.AuthUserClient;
import town.kairos.market.models.MarketModel;
import town.kairos.market.models.MarketUserModel;
import town.kairos.market.repositories.MarketUserRepository;
import town.kairos.market.services.MarketUserService;

import java.util.UUID;

@Service
public class MarketUserServiceImpl implements MarketUserService {

    @Autowired
    private MarketUserRepository marketUserRepository;

    @Autowired
    AuthUserClient authUserClient;

    @Override
    public boolean existsByMarketAndUserId(MarketModel marketModel, UUID userId) {
        return marketUserRepository.existsByMarketAndUserId(marketModel, userId);
    }

    @Override
    public MarketUserModel save(MarketUserModel marketUserModel) {
        return marketUserRepository.save(marketUserModel);
    }

    @Transactional
    @Override
    public MarketUserModel saveAndSendParticipationUserInMarket(MarketUserModel marketUserModel) {

        marketUserModel = marketUserRepository.save(marketUserModel);

        authUserClient.postParticipationUserInMarket(
                marketUserModel.getMarket().getMarketId(), marketUserModel.getUserId());

        return marketUserModel;
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return marketUserRepository.existsByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteMarketUserByUser(UUID userId) {
        marketUserRepository.deleteAllByUserId(userId);
    }
}
