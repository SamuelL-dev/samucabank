package samucabank.apibank.domain.service.operations.transaction;

import org.springframework.stereotype.Component;

@Component
public class SenderBalanceOperation implements TransactionOperation {
    @Override
    public void apply(TransactionOperationArgs args) {
        args.sender().setBalance(args.sender().getBalance() - args.amount());
    }
}
