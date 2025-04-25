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
        final int walletBalance = args.wallet().getBalance();
        final int amount = args.data().amount();

        if (getType() == args.data().type() && amount > walletBalance) {
            throw new InsufficientBalanceException(
                "Insufficient balance to complete the transaction. Please top up your account to continue."
            );
        }
    }
}
