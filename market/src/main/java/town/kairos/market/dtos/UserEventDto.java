package town.kairos.market.dtos;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import town.kairos.market.models.UserModel;

import java.util.UUID;

@Data
public class UserEventDto {
    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private String userStatus;
    private String userType;
    private String phoneNumber;
    private String documentId;
    private String imageUrl;
    private String actionType;

    public UserModel convertToUserModel() {
        var userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel);
        return userModel;
    }
}
