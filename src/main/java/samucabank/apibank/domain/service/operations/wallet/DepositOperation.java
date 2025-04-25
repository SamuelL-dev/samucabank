package samucabank.apibank.domain.service.operations.wallet;

import org.springframework.stereotype.Component;

@Component
public class DepositOperation implements CashFlowOperation {
    @Override
    public void apply(CashFlowOperationArgs args) {
            args.wallet().setBalance(args.wallet().getBalance() + args.data().amount());
    }
}