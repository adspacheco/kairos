package town.kairos.market.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import town.kairos.market.services.UtilsService;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    public String createUrlGetAllUsersByMarket(UUID marketId, Pageable pageable) {
        return "/users?marketId=" + marketId + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" +  pageable.getSort().toString().replaceAll(": ", ",");
    }

}
