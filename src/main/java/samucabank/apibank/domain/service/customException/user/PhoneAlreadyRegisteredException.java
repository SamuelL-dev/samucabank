package samucabank.apibank.domain.service.customException.user;

import java.io.Serial;

public class PhoneAlreadyRegisteredException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public PhoneAlreadyRegisteredException(String message) {
        super(message);
    }
}
