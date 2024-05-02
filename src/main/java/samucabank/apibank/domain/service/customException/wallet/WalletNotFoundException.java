package samucabank.apibank.domain.service.customException.wallet;

import java.io.Serial;

public class WalletNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;


    public WalletNotFoundException(String id) {
        super("The wallet id: " + id + " wasn't found on database");
    }
}
