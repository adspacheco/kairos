package town.kairos.authuser;

import org.springframework.data.jpa.repository.JpaRepository;
import town.kairos.authuser.models.UserModel;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
}
