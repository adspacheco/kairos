package town.kairos.authuser.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import town.kairos.authuser.clients.MarketClient;
import town.kairos.authuser.models.UserMarketModel;
import town.kairos.authuser.repositories.UserMarketRepository;
import town.kairos.authuser.repositories.UserRepository;
import town.kairos.authuser.models.UserModel;
import town.kairos.authuser.services.UserService;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMarketRepository userMarketRepository;

    @Autowired
    private MarketClient marketClient;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    @Override
    public void delete(UserModel userModel) {
        boolean deleteUserMarketInMarket = false;

        List<UserMarketModel> userMarketModelList = userMarketRepository.findAllUserMarketIntoUser(userModel.getUserId());

        if(!userMarketModelList.isEmpty()){
            userMarketRepository.deleteAll(userMarketModelList);
            deleteUserMarketInMarket = true;
        }

        userRepository.delete(userModel);

        if (deleteUserMarketInMarket) {
            marketClient.deleteUserInMarket(userModel.getUserId());
        }
    }

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

}
