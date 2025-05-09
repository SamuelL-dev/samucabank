package samucabank.apibank.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samucabank.apibank.api.config.swagger.documentation.TransactionSwagger;
import samucabank.apibank.api.dtos.request.TransactionRequest;
import samucabank.apibank.api.dtos.response.StatementDetailsResponse;
import samucabank.apibank.api.dtos.response.TransactionResponse;
import samucabank.apibank.domain.enums.transaction.TransactionStatus;
import samucabank.apibank.domain.model.Transaction;
import samucabank.apibank.domain.model.Wallet;
import samucabank.apibank.domain.repositories.TransactionRepository;
import samucabank.apibank.domain.service.serviceAction.TransactionService;
import samucabank.apibank.domain.service.serviceAction.WalletService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController implements TransactionSwagger {

    private final TransactionService service;

    private final TransactionRepository repository;

    private final WalletService walletService;

    @Override
    @GetMapping("/wallets/{walletId}/statement")
    public ResponseEntity<StatementDetailsResponse> getStatementByWalletId(@PathVariable("walletId") final Wallet walletId) {
        final Wallet wallet = walletService.findById(walletId.getId());

        final List<Transaction> transactionList = repository.findTop10BySenderIdOrderByTransactionDateDesc(wallet.getId());

        final List<Transaction> receivedTransactions = repository.findTop10ByReceiverIdOrderByTransactionDateDesc(wallet.getId());

        return ResponseEntity.ok(new StatementDetailsResponse(transactionList, receivedTransactions));
    }

    @Override
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransactionForWallet(@RequestBody @Valid final TransactionRequest request) {
        service.save(request);

        final TransactionResponse response = new TransactionResponse(TransactionStatus.SUCESS, request.description());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
