package town.kairos.market.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;
import town.kairos.market.clients.AuthUserClient;
import town.kairos.market.dtos.MarketDto;
import town.kairos.market.dtos.UserDto;
import town.kairos.market.enums.UserType;

import java.util.UUID;

@Component
public class MarketValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    AuthUserClient authUserClient;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        MarketDto marketDto = (MarketDto) target;
        validator.validate(marketDto, errors);

        if (!errors.hasErrors()) {
            validateUserCurator(marketDto.getUserCurator(), errors);
        }
    }

    private void validateUserCurator(UUID userCurator, Errors errors) {
        ResponseEntity<UserDto> responseUserCurator;

        try {
            responseUserCurator = authUserClient.getOneUserById(userCurator);

            if (responseUserCurator.getBody().getUserType().equals(UserType.INDIVIDUAL)) {
                errors.rejectValue("userCurator", "UserCuratorError", "User must be CURATOR or ADMIN.");
            }
        } catch (HttpStatusCodeException e) {
            if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                errors.rejectValue("userCurator", "UserCuratorError", "Curator not found.");
            }
        }
    }

}
