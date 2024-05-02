package samucabank.apibank.domain.service.customException.wallet;


import java.io.Serial;

public class WalletAlreadyRegisteredException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public WalletAlreadyRegisteredException(String message) {
        super(message);
    }
}
