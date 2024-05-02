package samucabank.apibank.domain.service.operations.transaction;

import org.springframework.stereotype.Component;

@Component
public class SenderBalanceOperation implements TransactionOperation {
    @Override
    public void applyTransactionOperation(TransactionOperationArgs args) {
        Integer newSenderBalance = args.sender().getBalance() - args.transactionAmount();
        args.sender().setBalance(newSenderBalance);
    }
}
