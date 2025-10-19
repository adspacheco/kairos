package town.kairos.market.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContextDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;
}
