package town.kairos.market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import town.kairos.market.models.MarketModel;

import java.util.UUID;

public interface MarketRepository extends JpaRepository<MarketModel, UUID>, JpaSpecificationExecutor<MarketModel> {

    @Query(value="select case when count(tmu) > 0 THEN true ELSE false END FROM tb_markets_users tmu WHERE tmu.market_id= :marketId and tmu.user_id= :userId",nativeQuery = true)
    boolean existsByMarketAndUser(@Param("marketId") UUID marketId, @Param("userId") UUID userId);


    @Modifying
    @Query(value="insert into tb_markets_users values (:marketId,:userId);",nativeQuery = true)
    void saveMarketUser(@Param("marketId") UUID marketId, @Param("userId") UUID userId);


}
