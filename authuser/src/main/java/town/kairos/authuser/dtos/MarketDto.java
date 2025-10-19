package town.kairos.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import town.kairos.authuser.enums.MarketStatus;
import town.kairos.authuser.enums.MarketType;

import java.util.UUID;

@Data
public class MarketDto {

    private UUID marketId;
    private String title;
    private String description;
    private MarketType marketType;
    private MarketStatus marketStatus;
    private String category;
    private String location;
    private String creationDate;
    private String lastUpdatedDate;

}

