package town.kairos.market.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import town.kairos.market.clients.MarketClient;
import town.kairos.market.dtos.UserDto;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MarketUserController {

    @Autowired
    MarketClient marketClient;

    @GetMapping("/markets/{marketId}/users")
    public ResponseEntity<Page<UserDto>> getAllUsersByMarket(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "marketId") UUID marketId) {
        return ResponseEntity.status(HttpStatus.OK).body(marketClient.getAllUsersByMarket(marketId, pageable));

    }
}
