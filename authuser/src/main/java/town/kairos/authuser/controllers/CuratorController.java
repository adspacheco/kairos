package town.kairos.authuser.controllers;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import town.kairos.authuser.dtos.CuratorDto;
import town.kairos.authuser.enums.UserType;
import town.kairos.authuser.models.UserModel;
import town.kairos.authuser.services.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/curators")
public class CuratorController {

    @Autowired
    private UserService userService;

    @PostMapping("/subscription")
    public ResponseEntity<Object> saveSubscriptionCurator(@RequestBody @Valid CuratorDto curatorDto) {
        Optional<UserModel> userModelOptional = userService.findById(curatorDto.getUserId());

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        var userModel = userModelOptional.get();
        userModel.setUserType(UserType.CURATOR);
        userModel.setLastUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updateUser(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }
}
