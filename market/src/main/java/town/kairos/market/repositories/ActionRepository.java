package town.kairos.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import town.kairos.market.models.ActionModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActionRepository extends JpaRepository<ActionModel, UUID>, JpaSpecificationExecutor<ActionModel> {

    @Query(value="select * from tb_actions where context_context_id = :contextId", nativeQuery = true)
    List<ActionModel> findAllActionsIntoContext(@Param("contextId") UUID contextId);

    @Query(value = "select * from tb_actions where context_context_id = :contextId and action_id = :actionId", nativeQuery = true)
    Optional<ActionModel> findActionIntoContext(@Param("contextId") UUID contextId, @Param("actionId") UUID actionId);
}
