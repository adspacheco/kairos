package town.kairos.market.dtos;

import lombok.Data;
import town.kairos.market.enums.UserStatus;
import town.kairos.market.enums.UserType;

import java.util.UUID;

@Data
public class UserDto {
    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private UserType userType;
    private UserStatus userStatus;
    private String phoneNumber;
    private String documentId;
    private String imageUrl;
}
