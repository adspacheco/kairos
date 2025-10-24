package town.kairos.authuser.services;

import town.kairos.authuser.enums.RoleType;
import town.kairos.authuser.models.RoleModel;

import java.util.Optional;

public interface RoleService {
    Optional<RoleModel> findByRoleName(RoleType roleType);
}
