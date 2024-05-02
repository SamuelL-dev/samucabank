package samucabank.apibank.domain.service.customException.card;

import java.io.Serial;

public class CardNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    public CardNotFoundException(String message) {
        super(message);
    }

    public CardNotFoundException(Long id) {
        this(String.format("There is no card registration with ID %d", id));
    }
}
