package town.kairos.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import town.kairos.market.repositories.MarketUserRepository;

@Service
public class MarketUserServiceImpl {

    @Autowired
    private MarketUserRepository marketUserRepository;
}
