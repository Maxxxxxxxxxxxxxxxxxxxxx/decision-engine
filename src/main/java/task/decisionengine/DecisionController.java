package task.decisionengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.decisionengine.domain.body.CreditRequest;
import task.decisionengine.domain.body.CreditResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/decision")
public class DecisionController {

    private final DecisionEngine engine;

    @Autowired public DecisionController(DecisionEngine e) {
        this.engine = e;
    }

    private static final Logger log = LoggerFactory.getLogger(DecisionController.class);

    @PostMapping
    ResponseEntity<CreditResponse> requestCredit(@RequestBody CreditRequest body) {
        log.info("Received credit request for user {}, amount: {}, period: {}", body.getUserCode(), body.getAmount(), body.getLoanPeriod());
        var engineResponse = engine.assessCreditScore(body);

        return ResponseEntity.status(HttpStatus.OK).body(engineResponse);
    }
}
