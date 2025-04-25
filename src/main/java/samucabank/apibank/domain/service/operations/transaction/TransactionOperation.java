package samucabank.apibank.domain.service.operations.transaction;

public interface TransactionOperation {

    void apply(TransactionOperationArgs args);

}
