package town.kairos.market.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import town.kairos.market.clients.AuthUserClient;
import town.kairos.market.dtos.ParticipationDto;
import town.kairos.market.dtos.UserDto;
import town.kairos.market.enums.UserStatus;
import town.kairos.market.models.MarketModel;
import town.kairos.market.models.MarketUserModel;
import town.kairos.market.services.MarketService;
import town.kairos.market.services.MarketUserService;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MarketUserController {

    @Autowired
    AuthUserClient authUserClient;

    @Autowired
    MarketService marketService;

    @Autowired
    MarketUserService marketUserService;

    @GetMapping("/markets/{marketId}/users")
    public ResponseEntity<Page<UserDto>> getAllUsersByMarket(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @PathVariable(value = "marketId") UUID marketId) {
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getAllUsersByMarket(marketId, pageable));
    }

    @PostMapping("/markets/{marketId}/users/participation")
    public ResponseEntity<Object> saveParticipationUserInMarket(@PathVariable(value = "marketId") UUID marketId,
                                                                @RequestBody @Valid ParticipationDto participationDto) {
        ResponseEntity<UserDto> responseUser;

        Optional<MarketModel> marketModelOptional = marketService.findById(marketId);
        if (marketModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }

        if(marketUserService.existsByMarketAndUserId(marketModelOptional.get(), participationDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: participation already exists!");
        }

        try {
            responseUser = authUserClient.getOneUserById(participationDto.getUserId());
            if(responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked!");
            }
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
        }

        MarketUserModel marketUserModel = marketUserService.saveAndSendParticipationUserInMarket(
                marketModelOptional.get().convertToMarketUserModel(participationDto.getUserId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(marketUserModel);
    }
}
