package samucabank.apibank.infrastructure.email;

import java.io.Serial;

public class EmailException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
