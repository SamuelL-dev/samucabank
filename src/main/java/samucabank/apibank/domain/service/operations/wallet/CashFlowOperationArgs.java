package samucabank.apibank.domain.service.operations.wallet;

import samucabank.apibank.api.dtos.request.BalanceOperationRequest;
import samucabank.apibank.domain.model.Wallet;

public record CashFlowOperationArgs(
    Wallet wallet,
    BalanceOperationRequest data
) {
}
