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
import town.kairos.market.dtos.ActionDto;
import town.kairos.market.models.ActionModel;
import town.kairos.market.models.ContextModel;
import town.kairos.market.services.ActionService;
import town.kairos.market.services.ContextService;
import town.kairos.market.specifications.SpecificationTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/contexts/{contextId}/actions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ActionController {

    @Autowired
    ActionService actionService;

    @Autowired
    ContextService contextService;

    @PostMapping
    public ResponseEntity<Object> saveAction(@PathVariable UUID contextId,
                                             @RequestBody @Valid ActionDto actionDto) {
        Optional<ContextModel> contextOpt = contextService.findById(contextId);
        if (contextOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Context not found.");
        }

        var action = new ActionModel();
        BeanUtils.copyProperties(actionDto, action);
        action.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        action.setContext(contextOpt.get());
        actionService.save(action);

        return ResponseEntity.status(HttpStatus.CREATED).body(action);
    }

    @GetMapping
    public ResponseEntity<Page<ActionModel>> getAllActions(
            SpecificationTemplate.ActionSpec spec,
            @PathVariable UUID contextId,
            @PageableDefault(page = 0, size = 10, sort = "actionId", direction = Sort.Direction.ASC) Pageable pageable) {

        var specContext = SpecificationTemplate.actionContextId(contextId).and(spec);
        Page<ActionModel> actions = actionService.findAllByContext(specContext, pageable);
        return ResponseEntity.ok(actions);
    }

    @GetMapping("/{actionId}")
    public ResponseEntity<Object> getOneAction(@PathVariable UUID contextId, @PathVariable UUID actionId) {
        Optional<ActionModel> opt = actionService.findActionIntoContext(contextId, actionId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Action not found.");
        }
        return ResponseEntity.ok(opt.get());
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity<Object> deleteAction(@PathVariable UUID contextId, @PathVariable UUID actionId) {
        Optional<ActionModel> opt = actionService.findActionIntoContext(contextId, actionId);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Action not found.");
        }
        actionService.delete(opt.get());
        return ResponseEntity.ok("Action deleted successfully.");
    }
}
