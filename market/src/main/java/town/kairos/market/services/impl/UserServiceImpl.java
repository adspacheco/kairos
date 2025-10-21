package town.kairos.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import town.kairos.market.repositories.UserRepository;
import town.kairos.market.services.UserService;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository marketUserRepository;

}
