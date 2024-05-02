package samucabank.apibank.domain.service.customException.user;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String id) {
        super("The user id: " + id + " wasn't found on database");
    }
}
