package town.kairos.market.dtos;

import town.kairos.market.enums.MarketType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class MarketDto {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String category;
    private String location;

    @NotNull
    private MarketType marketType;

    @NotNull
    private UUID userCurator;
}
