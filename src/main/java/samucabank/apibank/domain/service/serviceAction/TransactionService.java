package samucabank.apibank.domain.service.serviceAction;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import samucabank.apibank.api.dtos.request.TransactionRequest;
import samucabank.apibank.domain.model.Transaction;
import samucabank.apibank.domain.model.Wallet;
import samucabank.apibank.domain.repositories.TransactionRepository;
import samucabank.apibank.domain.service.businessRule.wallet.transaction.TransactionValidator;
import samucabank.apibank.domain.service.businessRule.wallet.transaction.TransactionValidatorArgs;
import samucabank.apibank.domain.service.operations.transaction.TransactionOperation;
import samucabank.apibank.domain.service.operations.transaction.TransactionOperationArgs;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    private final WalletService walletService;

    private final List<TransactionOperation> operations;

    private final List<TransactionValidator> validators;


    @Transactional
    public void save(TransactionRequest data) {
        final Wallet sender = walletService.findById(data.senderId());

        final Integer amount = data.amount();

        final Wallet receiver = walletService.findById(data.receiverId());

        validators.forEach(it -> it.validate(
            new TransactionValidatorArgs(
                sender,
                receiver,
                amount
            ))
        );

        final Transaction transaction = this.create(sender, receiver, amount);

        operations.forEach(it -> it.apply(
            new TransactionOperationArgs(
                sender,
                receiver,
                amount
            )
        ));

        repository.save(transaction);

        transaction.paymentConfirmedEvent();
    }

    private Transaction create(
        Wallet sender,
        Wallet receiver,
        Integer amount
    ) {
        return Transaction.builder()
            .sender(sender)
            .receiver(receiver)
            .amount(amount)
            .build();
    }
}
