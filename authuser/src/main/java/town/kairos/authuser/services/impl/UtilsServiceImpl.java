package town.kairos.authuser.services.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import town.kairos.authuser.services.UtilsService;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {


    public String createUrlGetAllMarketsByUser(UUID userId, Pageable pageable) {
        return  "/markets?userId=" + userId + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize()
                + "&sort=" +  pageable.getSort().toString().replaceAll(": ", ",");
    }

}
