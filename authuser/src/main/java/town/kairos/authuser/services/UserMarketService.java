package town.kairos.authuser.services;

import town.kairos.authuser.models.UserMarketModel;
import town.kairos.authuser.models.UserModel;

import java.util.UUID;

public interface UserMarketService {
    boolean existsByUserAndMarketId(UserModel userModel, UUID marketId);

    UserMarketModel save(UserMarketModel userMarketModel);
}
