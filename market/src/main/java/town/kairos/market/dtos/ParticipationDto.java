package town.kairos.market.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ParticipationDto {

    @NotNull
    private UUID userId;
}
