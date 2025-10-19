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
import town.kairos.market.dtos.ContextDto;
import town.kairos.market.models.ContextModel;
import town.kairos.market.models.MarketModel;
import town.kairos.market.services.ContextService;
import town.kairos.market.services.MarketService;
import town.kairos.market.specifications.SpecificationTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/markets/{marketId}/contexts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContextController {

    @Autowired
    ContextService contextService;

    @Autowired
    MarketService marketService;

    @PostMapping
    public ResponseEntity<Object> saveContext(@PathVariable UUID marketId,
                                              @RequestBody @Valid ContextDto contextDto) {
        Optional<MarketModel> marketOpt = marketService.findById(marketId);
        if (marketOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Market not found.");
        }

        var context = new ContextModel();
        BeanUtils.copyProperties(contextDto, context);
        context.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        context.setMarket(marketOpt.get());
        contextService.save(context);

        return ResponseEntity.status(HttpStatus.CREATED).body(context);
    }

    @GetMapping
    public ResponseEntity<Page<ContextModel>> getAllContexts(
            SpecificationTemplate.ContextSpec spec,
            @PathVariable UUID marketId,
            @PageableDefault(page = 0, size = 10, sort = "contextId", direction = Sort.Direction.ASC) Pageable pageable) {

        var specMarket = SpecificationTemplate.contextMarketId(marketId).and(spec);
        Page<ContextModel> contexts = contextService.findAllByMarket(specMarket, pageable);
        return ResponseEntity.ok(contexts);
    }

    @GetMapping("/{contextId}")
    public ResponseEntity<Object> getOneContext(@PathVariable UUID marketId, @PathVariable UUID contextId) {
        Optional<ContextModel> opt = contextService.findContextIntoMarket(marketId, contextId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Context not found.");
        }
        return ResponseEntity.ok(opt.get());
    }

    @DeleteMapping("/{contextId}")
    public ResponseEntity<Object> deleteContext(@PathVariable UUID marketId, @PathVariable UUID contextId) {
        Optional<ContextModel> opt = contextService.findContextIntoMarket(marketId, contextId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Context not found.");
        }
        contextService.delete(opt.get());
        return ResponseEntity.ok("Context deleted successfully.");
    }
}
