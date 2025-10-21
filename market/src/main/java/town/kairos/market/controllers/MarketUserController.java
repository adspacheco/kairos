package town.kairos.market.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import town.kairos.market.dtos.ParticipationDto;
import town.kairos.market.models.MarketModel;
import town.kairos.market.services.MarketService;
import town.kairos.market.services.UserService;
import town.kairos.market.specifications.SpecificationTemplate;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MarketUserController {

    @Autowired
    MarketService marketService;

    @Autowired
    UserService userService;

    @GetMapping("/markets/{marketId}/users")
    public ResponseEntity<Object> getAllUsersByMarket(SpecificationTemplate.UserSpec spec,
                                                      @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                      @PathVariable(value = "marketId") UUID marketId) {

        Optional<MarketModel> marketModelOptional = marketService.findById(marketId);
        if (marketModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAll(SpecificationTemplate.userMarketId(marketId).and(spec), pageable));
    }

    @PostMapping("/markets/{marketId}/users/participation")
    public ResponseEntity<Object> saveParticipationUserInMarket(@PathVariable(value = "marketId") UUID marketId,
                                                                @RequestBody @Valid ParticipationDto participationDto) {

        Optional<MarketModel> marketModelOptional = marketService.findById(marketId);

        if (marketModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }

        // state transfer verifications
        return ResponseEntity.status(HttpStatus.CREATED).body("to impl");
    }

}
