package samucabank.apibank.domain.service.businessRule.wallet.transaction;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.transaction.TransactionIdEqualityException;

@Component
public class TransactionIdEqualityChecker implements TransactionValidator {
    @Override
    public void validate(TransactionValidatorArgs args) {
        if(args.receiver() == args.sender()) {
            throw new TransactionIdEqualityException(
                "Transactions cannot have the same ID. Make sure each transaction has a unique ID."
            );
        }
    }
}