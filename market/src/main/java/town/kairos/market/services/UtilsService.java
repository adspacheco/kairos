package town.kairos.market.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {
   String createUrlGetAllUsersByMarket(UUID marketId, Pageable pageable);
}
