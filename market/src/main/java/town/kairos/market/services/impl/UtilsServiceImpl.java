package town.kairos.market.services.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import town.kairos.market.services.UtilsService;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    String REQUEST_URI = "http://localhost:8087";

    public String createUrl(UUID marketId, Pageable pageable) {
        return REQUEST_URI + "/users?marketId=" + marketId + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" +  pageable.getSort().toString().replaceAll(": ", ",");
    }

}
