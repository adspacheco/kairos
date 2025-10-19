package town.kairos.market.controllers;

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
import town.kairos.market.dtos.MarketDto;
import town.kairos.market.enums.MarketStatus;
import town.kairos.market.models.MarketModel;
import town.kairos.market.services.MarketService;
import town.kairos.market.specifications.SpecificationTemplate;

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
        log.info("Market created successfully ID {}", marketModel.getMarketId());
        return ResponseEntity.status(HttpStatus.CREATED).body(marketModel);
    }

    @PutMapping("/{marketId}")
    public ResponseEntity<Object> updateMarket(@PathVariable UUID marketId,
                                               @RequestBody @Valid MarketDto marketDto) {
        Optional<MarketModel> opt = marketService.findById(marketId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }
        var market = opt.get();
        BeanUtils.copyProperties(marketDto, market);
        market.setLastUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        marketService.save(market);

        log.info("Market updated successfully ID {}", marketId);
        return ResponseEntity.ok(market);
    }

    @DeleteMapping("/{marketId}")
    public ResponseEntity<Object> deleteMarket(@PathVariable UUID marketId) {
        Optional<MarketModel> opt = marketService.findById(marketId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }
        marketService.delete(opt.get());
        return ResponseEntity.ok("Market deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<Page<MarketModel>> getAllMarkets(
            SpecificationTemplate.MarketSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "marketId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<MarketModel> markets = marketService.findAll(spec, pageable);
        return ResponseEntity.ok(markets);
    }

    @GetMapping("/{marketId}")
    public ResponseEntity<Object> getOneMarket(@PathVariable UUID marketId) {
        Optional<MarketModel> opt = marketService.findById(marketId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }
        return ResponseEntity.ok(opt.get());
    }
}
