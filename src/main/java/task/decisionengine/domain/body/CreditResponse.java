package task.decisionengine.domain.body;

import lombok.Builder;
import lombok.Data;
import task.decisionengine.domain.data.entity.Credit;

@Data
@Builder
public class CreditResponse {
    private Credit credit;
    private Boolean decision;
    private String message;

    // Helper for denied credit requests
    // Credit field is null to prevent accidental save to db
    public static CreditResponse denied() {
        return CreditResponse.builder().decision(false).build();
    }

    public static CreditResponse denied(String message) {
        return CreditResponse.builder().decision(false).message(message).build();
    }
}
