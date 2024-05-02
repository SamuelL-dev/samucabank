package samucabank.apibank.domain.service.businessRule.wallet.transaction;

import samucabank.apibank.domain.model.Wallet;

public record TransactionValidatorArgs(Wallet sender, Wallet receiver, Integer amount) {
}
