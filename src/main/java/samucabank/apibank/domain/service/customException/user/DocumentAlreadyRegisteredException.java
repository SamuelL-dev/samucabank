package samucabank.apibank.domain.service.customException.user;

import java.io.Serial;

public class DocumentAlreadyRegisteredException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;


    public DocumentAlreadyRegisteredException(String message) {
        super(message);
    }
}
