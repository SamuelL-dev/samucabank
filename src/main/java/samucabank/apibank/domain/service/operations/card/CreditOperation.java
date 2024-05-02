package samucabank.apibank.domain.service.operations.card;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.enuns.card.CardOperationType;

@Component
public class CreditOperation implements CardOperation {


    @Override
    public CardOperationType getType() {
        return CardOperationType.CREDIT;
    }

    @Override
    public void applyCardTransactionOperation(CardOperationArgs args) {
        if (args.data().type() == getType()) {
            Integer newAmountSpent = args.card().getAmountSpent() + args.data().amount();
            args.card().setAmountSpent(newAmountSpent);
        }
    }
}

