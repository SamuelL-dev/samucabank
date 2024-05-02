package samucabank.apibank.domain.service.businessRule.wallet.transaction;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.transaction.InsufficientBalanceException;

@Component
public class WalletBalanceValidator implements TransactionValidator{

    @Override
    public void validate(final TransactionValidatorArgs args) {
        if(args.sender().getBalance().compareTo(args.amount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to complete the transaction. Please top up your account to continue.");
        }
    }
}
