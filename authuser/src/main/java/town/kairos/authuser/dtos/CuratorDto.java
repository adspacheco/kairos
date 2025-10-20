package town.kairos.authuser.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CuratorDto {

    @NotNull
    private UUID userId;
}
