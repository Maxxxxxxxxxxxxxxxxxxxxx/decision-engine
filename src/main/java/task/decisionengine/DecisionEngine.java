package task.decisionengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import task.decisionengine.domain.body.CreditRequest;
import task.decisionengine.domain.body.CreditResponse;
import task.decisionengine.domain.data.UserRepository;
import task.decisionengine.domain.data.entity.Credit;
import task.decisionengine.exception.BadCreditDataException;

@Service
@ConfigurationProperties(prefix = "engine")
public class DecisionEngine {

    @Autowired private UserRepository userRepository;

    @Autowired public DecisionEngine(UserRepository r) {
        this.userRepository = r;
    }

    private static final Double MIN_SUM = 2000.0;
    private static final Double MAX_SUM = 10000.0;
    private static final Integer MIN_PERIOD = 12;
    private static final Integer MAX_PERIOD = 60;

    private static final Logger log = LoggerFactory.getLogger(DecisionEngine.class);

    private Double calculateMaxCreditAmount(Double modifier, Integer period) throws BadCreditDataException {

        if (period < MIN_PERIOD) throw new BadCreditDataException("Duration is below minimum value (" + MIN_PERIOD + " months)");
        if (period > MAX_PERIOD) throw new BadCreditDataException("Duration exceeds maximum value (" + MAX_PERIOD + " months)");

        var calculatedValue = modifier * period;
        if (calculatedValue <= MAX_SUM) return calculatedValue;
        else return MAX_SUM;
    }

    public CreditResponse assessCreditScore(CreditRequest data) {
        if (data.getAmount() < MIN_SUM) return CreditResponse.denied("Amount is below minimum sum (" + MIN_SUM + ").");

        var userData = userRepository.findByUserCode(data.getUserCode());

        if (userData == null) return CreditResponse.denied("User not found");

        if (userData.getCredit() != null) return CreditResponse.denied("User already has debt");
        else {
            try {
                var maxCreditAmount = calculateMaxCreditAmount(userData.getCreditModifier(), data.getLoanPeriod());

                if (maxCreditAmount <= 0) return CreditResponse.denied("Amount must be greater than 0");

                if (data.getAmount().equals(maxCreditAmount) || data.getAmount() < maxCreditAmount) {
                    var credit = Credit.builder().duration(data.getLoanPeriod()).amount(data.getAmount()).build();

                    userData.setCredit(credit);
                    userRepository.save(userData);

                    return CreditResponse.builder().credit(credit).decision(true).build();
                }

                var credit = Credit.builder().amount(maxCreditAmount).duration(data.getLoanPeriod()).build();
                return CreditResponse.builder().decision(true).credit(credit).build();
            } catch (Exception e) {
                log.error("An error has occurred during calculating credit score. Message: {}", e.getMessage());
                return CreditResponse.denied(e.getMessage());
            }
        }
    }
}
