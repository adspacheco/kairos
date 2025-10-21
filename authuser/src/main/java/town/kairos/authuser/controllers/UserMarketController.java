package town.kairos.authuser.controllers;

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
import town.kairos.authuser.clients.MarketClient;
import town.kairos.authuser.dtos.MarketDto;
import town.kairos.authuser.dtos.UserMarketDto;
import town.kairos.authuser.models.UserMarketModel;
import town.kairos.authuser.models.UserModel;
import town.kairos.authuser.services.UserMarketService;
import town.kairos.authuser.services.UserService;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserMarketController {

    @Autowired
    MarketClient marketClient;

    @Autowired
    UserService userService;

    @Autowired
    UserMarketService userMarketService;

    @GetMapping("/users/{userId}/markets")
    public ResponseEntity<Page<MarketDto>> getAllMarketsByUser(@PageableDefault(page = 0, size = 10, sort = "marketId", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(marketClient.getAllMarketsByUser(userId, pageable));
    }

    @PostMapping("/users/{userId}/markets/participation")
    public ResponseEntity<Object> saveParticipationUserInMarket(@PathVariable(value = "userId") UUID userId,
                                                                @RequestBody @Valid UserMarketDto userMarketDto) {

        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        if (userMarketService.existsByUserAndMarketId(userModelOptional.get(), userMarketDto.getMarketId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: participation already exists!");
        }

        UserMarketModel userMarketModel = userMarketService.save(userModelOptional.get().convertToUserCourseModel(userMarketDto.getMarketId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(userMarketModel);
    }

    @DeleteMapping("/users/markets/{marketId}")
    public ResponseEntity<Object> deleteUserMarketByMarket(@PathVariable(value = "marketId") UUID marketId) {
        if(!userMarketService.existsByMarketId(marketId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserMarket not found!");
        }

        userMarketService.deleteUserMarketByMarket(marketId);
        return ResponseEntity.status(HttpStatus.OK).body("UserMarket deleted successfully!");
    }
}
