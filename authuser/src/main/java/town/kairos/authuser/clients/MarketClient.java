package town.kairos.authuser.clients;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import town.kairos.authuser.dtos.MarketDto;
import town.kairos.authuser.dtos.ResponsePageDto;
import town.kairos.authuser.services.UtilsService;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class MarketClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${kairos.api.url.market}")
    String REQUEST_URL_MARKET;

    public Page<MarketDto> getAllMarketsByUser(UUID userId, Pageable pageable) {
        List<MarketDto> searchResult = null;
        ResponseEntity<ResponsePageDto<MarketDto>> result = null;

        String url = REQUEST_URL_MARKET + utilsService.createUrlGetAllMarketsByUser(userId, pageable);

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<MarketDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<MarketDto>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {}", searchResult.size());
        } catch(HttpStatusCodeException e) {
            log.error("Error request /markets {}", e);
        }
        log.info("Ending request /markets userId {}", userId);

        return result.getBody();
    }
}
