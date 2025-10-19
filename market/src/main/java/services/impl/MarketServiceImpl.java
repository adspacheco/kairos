package services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.MarketRepository;
import services.MarketService;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketRepository marketRepository;
}
