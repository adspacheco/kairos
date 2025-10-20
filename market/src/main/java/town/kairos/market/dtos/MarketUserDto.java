package town.kairos.market.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class MarketUserDto {

    private UUID marketId;
    private UUID userId;
}
