package town.kairos.market.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import town.kairos.market.dtos.MarketDto;
import town.kairos.market.enums.UserType;
import town.kairos.market.models.UserModel;
import town.kairos.market.services.UserService;

import java.util.Optional;
import java.util.UUID;

@Component
public class MarketValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    UserService userService;

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
        Optional<UserModel> userModelOptional = userService.findById(userCurator);

        if (!userModelOptional.isPresent()) {
            errors.rejectValue("userCurator", "UserCuratorError", "Curator not found.");
        }

        if (userModelOptional.get().getUserType().equals(UserType.INDIVIDUAL.toString())) {
            errors.rejectValue("userCurator", "UserCuratorError", "User must be CURATOR or ADMIN.");
        }

    }

}
