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

    private final TransactionRepository transactionRepository;

    private final WalletService walletService;

    private final List<TransactionOperation> transactionOperation;

    private final List<TransactionValidator> transactionValidator;



    @Transactional
    public void createTransaction(final TransactionRequest data) {
        final Wallet sender = walletService.findById(data.senderId());

        final Integer amount = data.amount();

        final Wallet receiver = walletService.findById(data.receiverId());

        this.transactionValidator.forEach
                (it -> it.validate(new TransactionValidatorArgs(
                        sender,
                        receiver,
                        amount
                )));

        final Transaction transaction = this.createNewTransaction(sender, receiver, amount);

        this.transactionOperation.forEach
                (it -> it.applyTransactionOperation(new TransactionOperationArgs(
                        sender,
                        receiver,
                        amount
                )));

        transaction.paymentConfirmedEvent();

        this.transactionRepository.save(transaction);

    }

    private Transaction createNewTransaction(final Wallet sender, final Wallet receiver, final Integer amount) {
        return Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .build();
    }
}
