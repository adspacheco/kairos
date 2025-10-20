package town.kairos.authuser.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UserMarketDto {

    private UUID userId;
    @NotNull
    private UUID marketId;
}
