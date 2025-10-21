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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import town.kairos.market.dtos.MarketDto;
import town.kairos.market.enums.MarketStatus;
import town.kairos.market.models.MarketModel;
import town.kairos.market.services.MarketService;
import town.kairos.market.specifications.SpecificationTemplate;
import town.kairos.market.validation.MarketValidator;

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

    @Autowired
    MarketValidator marketValidator;

    @PostMapping
    public ResponseEntity<Object> saveMarket(@RequestBody MarketDto marketDto, Errors errors) {
        log.debug("POST saveMarket received: {}", marketDto.toString());

        marketValidator.validate(marketDto, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }

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
        Optional<MarketModel> marketModelOptional = marketService.findById(marketId);
        if (marketModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }
        var market = marketModelOptional.get();
        BeanUtils.copyProperties(marketDto, market);
        market.setLastUpdatedDate(LocalDateTime.now(ZoneId.of("UTC")));
        marketService.save(market);

        log.info("Market updated successfully ID {}", marketId);
        return ResponseEntity.ok(market);
    }

    @DeleteMapping("/{marketId}")
    public ResponseEntity<Object> deleteMarket(@PathVariable UUID marketId) {
        log.debug("DELETE deleteMarket marketId received: {}", marketId);
        Optional<MarketModel> opt = marketService.findById(marketId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }
        marketService.delete(opt.get());
        log.debug("DELETE deleteMarket marketId deleted {}", marketId);
        log.info("Market deleted successfully marketId {}", marketId);
        return ResponseEntity.status(HttpStatus.OK).body("Market deleted successfully.");
    }

    @GetMapping
    public ResponseEntity<Page<MarketModel>> getAllMarkets(
            SpecificationTemplate.MarketSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "marketId", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) UUID userId) {

            return ResponseEntity.status(HttpStatus.OK).body(marketService.findAll(spec, pageable));
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
