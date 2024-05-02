package samucabank.apibank.domain.service.customException.user;

public class ZipCodeDoesNotExistException extends RuntimeException {



    public ZipCodeDoesNotExistException(String message) {
        super(message);
    }
}
