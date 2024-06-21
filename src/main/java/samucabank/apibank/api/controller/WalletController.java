package samucabank.apibank.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samucabank.apibank.api.config.swagger.documentation.WalletSwagger;
import samucabank.apibank.api.dtos.request.BalanceOperationRequest;
import samucabank.apibank.api.dtos.response.WalletResponse;
import samucabank.apibank.domain.service.serviceAction.WalletService;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController implements WalletSwagger {

    private final WalletService walletService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> findById(@PathVariable("id") final String id) {
        final WalletResponse wallet = walletService.findByIdDTO(id);
        return ResponseEntity.ok(wallet);
    }

    @Override
    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Void> processDeposit(@RequestBody @Valid final BalanceOperationRequest request,
                                               @PathVariable("walletId") final String walletId) {
        walletService.processDeposit(walletId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PostMapping("/users/{userId}")
    public ResponseEntity<WalletResponse> createWalletForUser(@PathVariable("userId") final String userId) {
        walletService.save(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}