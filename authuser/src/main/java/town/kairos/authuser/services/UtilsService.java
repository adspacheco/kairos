package town.kairos.authuser.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {
   String createUrlGetAllMarketsByUser(UUID userId, Pageable pageable);
}
