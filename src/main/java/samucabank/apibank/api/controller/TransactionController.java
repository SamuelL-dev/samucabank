package samucabank.apibank.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    private final WalletService walletService;


    @GetMapping("/wallets/{walletId}/statement")
    @Operation(description = "Operation to find the statement by wallet ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statement found successfully"),
            @ApiResponse(responseCode = "400", description = "Wallet id not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<StatementDetailsResponse> getStatementByWalletId(@PathVariable("walletId") final String walletId) {
        final Wallet wallet = walletService.findById(walletId);

        final List<Transaction> transactionList = transactionRepository.findTop10BySenderIdOrderByTransactionDateDesc(wallet.getId());

        final List<Transaction> receivedTransactions = transactionRepository.findTop10ByReceiverIdOrderByTransactionDateDesc(wallet.getId());

        final StatementDetailsResponse response = new StatementDetailsResponse(transactionList, receivedTransactions);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(description = "Operation to create a transaction for a wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "422", description = "Insufficient balance to complete the transaction"),
            @ApiResponse(responseCode = "400", description = "Wallet id not found"),
            @ApiResponse(responseCode = "422", description = "Transaction value must be greater than 0"),
            @ApiResponse(responseCode = "500", description = "Internal error server")
    })
    public ResponseEntity<TransactionResponse> createTransactionForWallet(@RequestBody @Valid final TransactionRequest request) {
        transactionService.save(request);

        final TransactionResponse response = new TransactionResponse(TransactionStatus.SUCESS, request.description());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
