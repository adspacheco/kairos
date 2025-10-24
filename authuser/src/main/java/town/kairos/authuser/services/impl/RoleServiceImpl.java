package town.kairos.authuser.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import town.kairos.authuser.enums.RoleType;
import town.kairos.authuser.models.RoleModel;
import town.kairos.authuser.repositories.RoleRepository;
import town.kairos.authuser.services.RoleService;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Optional<RoleModel> findByRoleName(RoleType roleType) {
        return roleRepository.findByRoleName(roleType);
    }
}
