package samucabank.apibank.domain.service.operations.transaction;

import org.springframework.stereotype.Component;

@Component
public class ReceiverBalanceOperation implements TransactionOperation{
    @Override
    public void applyTransactionOperation(TransactionOperationArgs args) {
        Integer newReceiverBalance = args.receiver().getBalance() + args.transactionAmount();
        args.receiver().setBalance(newReceiverBalance);
    }
}
