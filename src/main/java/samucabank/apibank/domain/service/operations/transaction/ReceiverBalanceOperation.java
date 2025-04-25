package samucabank.apibank.domain.service.operations.transaction;

import org.springframework.stereotype.Component;

@Component
public class ReceiverBalanceOperation implements TransactionOperation{
    @Override
    public void apply(TransactionOperationArgs args) {
        args.receiver().setBalance(args.receiver().getBalance() + args.amount());
    }
}
