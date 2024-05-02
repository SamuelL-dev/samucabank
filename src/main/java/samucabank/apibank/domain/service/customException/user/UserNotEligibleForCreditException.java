package samucabank.apibank.domain.service.customException.user;

import java.io.Serial;

public class UserNotEligibleForCreditException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotEligibleForCreditException(String message) {
        super(message);
    }
}
