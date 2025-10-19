package town.kairos.authuser.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import town.kairos.authuser.repositories.UserMarketRepository;
import town.kairos.authuser.services.UserMarketService;

@Service
public class UserMarketServiceImpl implements UserMarketService {

    @Autowired
    UserMarketRepository userMarketRepository;
}
