package town.kairos.authuser.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import town.kairos.authuser.models.UserMarketModel;
import town.kairos.authuser.models.UserModel;
import town.kairos.authuser.repositories.UserMarketRepository;
import town.kairos.authuser.services.UserMarketService;

import java.util.UUID;

@Service
public class UserMarketServiceImpl implements UserMarketService {

    @Autowired
    UserMarketRepository userMarketRepository;

    @Override
    public boolean existsByUserAndMarketId(UserModel userModel, UUID marketId) {
        return userMarketRepository.existsByUserAndMarketId(userModel, marketId);
    }

    @Override
    public UserMarketModel save(UserMarketModel userMarketModel) {
        return userMarketRepository.save(userMarketModel);
    }

    @Override
    public boolean existsByMarketId(UUID marketId) {
        return userMarketRepository.existsByMarketId(marketId);
    }

    @Transactional
    @Override
    public void deleteUserMarketByMarket(UUID marketId) {
        userMarketRepository.deleteAllByMarketId(marketId);
    }
}
