package samucabank.apibank.domain.service.customException.user;

import java.io.Serial;

public class EmailAlreadyRegisteredException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
}
