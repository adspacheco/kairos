package town.kairos.authuser.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import town.kairos.authuser.clients.MarketClient;
import town.kairos.authuser.dtos.MarketDto;
import town.kairos.authuser.services.UserService;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserMarketController {

    @Autowired
    MarketClient marketClient;

    @Autowired
    UserService userService;

    @GetMapping("/users/{userId}/markets")
    public ResponseEntity<Page<MarketDto>> getAllMarketsByUser(@PageableDefault(page = 0, size = 10, sort = "marketId", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(marketClient.getAllMarketsByUser(userId, pageable));
    }

}
