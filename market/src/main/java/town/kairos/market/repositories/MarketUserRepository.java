package town.kairos.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import town.kairos.market.models.MarketModel;
import town.kairos.market.models.MarketUserModel;

import java.util.List;
import java.util.UUID;

public interface MarketUserRepository extends JpaRepository<MarketUserModel, UUID> {

    boolean existsByMarketAndUserId(MarketModel marketModel, UUID userId);

    @Query(value="select * from tb_markets_users where market_market_id = :marketId", nativeQuery = true)
    List<MarketUserModel> findAllMarketUserIntoMarket(@Param("marketId") UUID marketId);

}
