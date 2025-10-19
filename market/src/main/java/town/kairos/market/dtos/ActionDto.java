package town.kairos.market.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActionDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private String link;

    private String mediaUrl;
}
