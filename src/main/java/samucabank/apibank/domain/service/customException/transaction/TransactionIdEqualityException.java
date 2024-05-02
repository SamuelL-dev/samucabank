package samucabank.apibank.domain.service.customException.transaction;

public class TransactionIdEqualityException extends RuntimeException {

    public TransactionIdEqualityException(String message) {
        super(message);
    }
}
