package samucabank.apibank.domain.service.customException.card;

import java.io.Serial;

public class CardAlreadyRegisteredException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CardAlreadyRegisteredException(String message) {
        super(message);
    }
}
