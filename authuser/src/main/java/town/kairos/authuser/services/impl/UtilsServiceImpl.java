package town.kairos.authuser.services.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import town.kairos.authuser.services.UtilsService;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    String REQUEST_URI = "http://localhost:8088";

    public String createUrl(UUID userId, Pageable pageable) {
        return REQUEST_URI + "/markets?userId=" + userId + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" +  pageable.getSort().toString().replaceAll(": ", ",");
    }

}
