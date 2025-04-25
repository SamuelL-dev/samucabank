package samucabank.apibank.domain.service.customException.user;

import java.io.Serial;

public class AgeEligibleForRegistrationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AgeEligibleForRegistrationException(String message) {
        super(message);
    }
}
