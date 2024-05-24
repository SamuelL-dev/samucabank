package samucabank.apibank.domain.service.customException.card;

import java.io.Serial;

public class CardPendingTransactionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CardPendingTransactionException(String message) {
        super(message);
    }
}
