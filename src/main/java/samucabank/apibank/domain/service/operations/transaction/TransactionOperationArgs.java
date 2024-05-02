package samucabank.apibank.domain.service.operations.transaction;

import samucabank.apibank.domain.model.Wallet;

public record TransactionOperationArgs(Wallet sender, Wallet receiver, Integer transactionAmount){
}
