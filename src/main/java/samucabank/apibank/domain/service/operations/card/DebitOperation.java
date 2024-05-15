package samucabank.apibank.domain.service.operations.card;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.enums.card.CardOperationType;

@Component
public class DebitOperation implements CardOperation {
    @Override
    public CardOperationType getType() {
        return CardOperationType.DEBIT;
    }

    @Override
    public void applyCardTransactionOperation(CardOperationArgs args) {
        if (args.data().type() == getType()) {
            Integer newBalance = args.wallet().getBalance() - args.data().amount();
            args.wallet().setBalance(newBalance);
        }

    }
}
