package samucabank.apibank.domain.service.customException.card;

import java.io.Serial;

public class CardLimitExceededException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CardLimitExceededException(String message) {
        super(message);
    }
}
