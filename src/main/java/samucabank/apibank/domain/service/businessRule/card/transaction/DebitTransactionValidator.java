package samucabank.apibank.domain.service.businessRule.card.transaction;


import org.springframework.stereotype.Component;
import samucabank.apibank.domain.enums.card.CardOperationType;
import samucabank.apibank.domain.service.customException.transaction.InsufficientBalanceException;

@Component
public class DebitTransactionValidator implements CardTransactionValidator {
    @Override
    public CardOperationType getType() {
        return CardOperationType.DEBIT;
    }

    @Override
    public void validate(CardTransactionArgs args) {
        Integer walletBalance = args.wallet().getBalance();
        Integer transactionAmount = args.request().amount();

        if(getType() == args.request().type() && transactionAmount > walletBalance) {
            throw new InsufficientBalanceException("Insufficient balance to complete the transaction. Please top up your account to continue.");
        }

    }
}
