package samucabank.apibank.domain.service.customException.wallet;

import java.io.Serial;

public class UnauthorizedOperationException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = 1L;

    public UnauthorizedOperationException(String message) {
        super(message);
    }
}
