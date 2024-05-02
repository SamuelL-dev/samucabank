package samucabank.apibank.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samucabank.apibank.api.dtos.request.BalanceOperationRequest;
import samucabank.apibank.api.dtos.response.WalletResponse;
import samucabank.apibank.domain.service.serviceAction.WalletService;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{id}")
    @Operation(summary = "Find wallet by ID", description = "Find a wallet by its ID", method = "GET")
    public ResponseEntity<WalletResponse> findById(@PathVariable("id") final String id) {
        final WalletResponse wallet = this.walletService.findByIdDTO(id);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/{walletId}/deposit")
    @Operation(summary = "Deposit operation", description = "Deposit balance to wallet", method = "POST")
    public ResponseEntity<Void> processDeposit(@RequestBody @Valid final BalanceOperationRequest request,
                                                 @PathVariable("walletId") final String walletId) {
        this.walletService.processDeposit(walletId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/users/{userId}")
    @Operation(summary = "Create wallet by user ID", description = "Creating a wallet by user ID", method = "POST")
    public ResponseEntity<WalletResponse> createWalletForUser(@PathVariable("userId") final String userId) {
        this.walletService.register(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
