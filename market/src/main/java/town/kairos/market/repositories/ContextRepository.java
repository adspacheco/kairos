package town.kairos.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import town.kairos.market.models.ContextModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContextRepository extends JpaRepository<ContextModel, UUID>, JpaSpecificationExecutor<ContextModel> {

    @Query(value="select * from tb_contexts where market_market_id = :marketId", nativeQuery = true)
    List<ContextModel> findAllContextsIntoMarket(@Param("marketId") UUID marketId);

    @Query(value = "select * from tb_contexts where market_market_id = :marketId and context_id = :contextId", nativeQuery = true)
    Optional<ContextModel> findContextIntoMarket(@Param("marketId") UUID marketId, @Param("contextId") UUID contextId);
}
