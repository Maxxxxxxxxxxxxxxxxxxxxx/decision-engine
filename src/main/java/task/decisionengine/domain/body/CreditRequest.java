package task.decisionengine.domain.body;

import lombok.Data;

@Data
public class CreditRequest {
    private String userCode;
    private Double amount;
    private Integer loanPeriod;
}
