package town.kairos.market.services;

import town.kairos.market.models.MarketModel;
import town.kairos.market.models.MarketUserModel;

import java.util.UUID;

public interface MarketUserService {
    boolean existsByMarketAndUserId(MarketModel marketModel, UUID userId);

    MarketUserModel save(MarketUserModel marketUserModel);

    MarketUserModel saveAndSendParticipationUserInMarket(MarketUserModel marketUserModel);
}
