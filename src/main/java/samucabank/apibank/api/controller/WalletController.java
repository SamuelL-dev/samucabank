package samucabank.apibank.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(description = "Operation to search for the wallet by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found successfully"),
            @ApiResponse(responseCode = "400", description = "Wallet not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WalletResponse> findById(@PathVariable("id") final String id) {
        final WalletResponse wallet = walletService.findByIdDTO(id);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/{walletId}/deposit")
    @Operation(description = "Operation to deposit balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deposit made successfully"),
            @ApiResponse(responseCode = "400", description = "Wallet not found"),
            @ApiResponse(responseCode = "422", description = "The deposit amount must be greater than 0"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> processDeposit(@RequestBody @Valid final BalanceOperationRequest request,
                                                 @PathVariable("walletId") final String walletId) {
        walletService.processDeposit(walletId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/users/{userId}")
    @Operation(description = "Operation to create a wallet for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wallet created successfully"),
            @ApiResponse(responseCode = "400", description = "The user id entered was not found"),
            @ApiResponse(responseCode = "406", description = "This user already has a wallet"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WalletResponse> createWalletForUser(@PathVariable("userId") final String userId) {
        walletService.save(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
