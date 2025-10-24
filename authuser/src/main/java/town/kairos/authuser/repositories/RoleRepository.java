package town.kairos.authuser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import town.kairos.authuser.enums.RoleType;
import town.kairos.authuser.models.RoleModel;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    Optional<RoleModel> findByRoleName(RoleType name);
}
