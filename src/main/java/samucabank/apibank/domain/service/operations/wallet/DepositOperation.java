package samucabank.apibank.domain.service.operations.wallet;

import org.springframework.stereotype.Component;

@Component
public class DepositOperation implements CashFlowOperation {
    @Override
    public void execute(CashFlowOperationArgs args) {
            Integer newBalance = args.wallet().getBalance() + args.request().amount();
            args.wallet().setBalance(newBalance);
    }
}
