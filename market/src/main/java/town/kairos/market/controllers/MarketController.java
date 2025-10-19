package town.kairos.market.controllers;

import town.kairos.market.dtos.MarketDto;
import town.kairos.market.enums.MarketStatus;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import town.kairos.market.models.MarketModel;
import town.kairos.market.services.MarketService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/markets")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MarketController {

    @Autowired
    private MarketService marketService;

    @PostMapping
    public ResponseEntity<Object> saveMarket(@RequestBody @Valid MarketDto marketDto) {
        log.debug("POST saveMarket received: {}", marketDto.toString());
        var marketModel = new MarketModel();
        BeanUtils.copyProperties(marketDto, marketModel);
        marketModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        marketModel.setLastUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        marketModel.setMarketStatus(MarketStatus.ACTIVE);

        marketService.save(marketModel);

        log.debug("POST saveMarket marketId saved: {}", marketModel.getMarketId());
        log.info("Market created successfully with ID {}", marketModel.getMarketId());
        return ResponseEntity.status(HttpStatus.CREATED).body(marketModel);
    }

    @DeleteMapping("/{marketId}")
    public ResponseEntity<Object> deleteMarket(@PathVariable(value = "marketId") UUID marketId) {
        log.debug("DELETE deleteMarket received ID: {}", marketId);
        Optional<MarketModel> marketModelOptional = marketService.findById(marketId);
        if (!marketModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }

        marketService.delete(marketModelOptional.get());
        log.debug("DELETE deleteMarket deleted ID: {}", marketId);
        log.info("Market deleted successfully ID {}", marketId);
        return ResponseEntity.status(HttpStatus.OK).body("Market deleted successfully.");
    }

    @PutMapping("/{marketId}")
    public ResponseEntity<Object> updateMarket(@PathVariable(value = "marketId") UUID marketId,
                                               @RequestBody @Valid MarketDto marketDto) {
        log.debug("PUT updateMarket received: {}", marketDto.toString());
        Optional<MarketModel> marketModelOptional = marketService.findById(marketId);
        if (!marketModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }

        var marketModel = marketModelOptional.get();
        marketModel.setTitle(marketDto.getTitle());
        marketModel.setDescription(marketDto.getDescription());
        marketModel.setCategory(marketDto.getCategory());
        marketModel.setLocation(marketDto.getLocation());
        marketModel.setMarketType(marketDto.getMarketType());
        marketModel.setLastUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));

        marketService.save(marketModel);

        log.debug("PUT updateMarket marketId saved: {}", marketModel.getMarketId());
        log.info("Market updated successfully ID {}", marketModel.getMarketId());
        return ResponseEntity.status(HttpStatus.OK).body(marketModel);
    }

    @GetMapping
    public ResponseEntity<Page<MarketModel>> getAllMarkets(
            @PageableDefault(page = 0, size = 10, sort = "marketId", direction = Sort.Direction.ASC) Pageable pageable) {
        log.debug("GET getAllMarkets called");
        return ResponseEntity.status(HttpStatus.OK).body(marketService.findAll(pageable));
    }

    @GetMapping("/{marketId}")
    public ResponseEntity<Object> getOneMarket(@PathVariable(value = "marketId") UUID marketId) {
        log.debug("GET getOneMarket called with ID {}", marketId);
        Optional<MarketModel> marketModelOptional = marketService.findById(marketId);
        if (!marketModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(marketModelOptional.get());
    }
}
