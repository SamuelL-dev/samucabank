package samucabank.apibank.api.config.swagger.documentation;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import samucabank.apibank.api.dtos.request.BalanceOperationRequest;
import samucabank.apibank.api.dtos.response.WalletResponse;
import samucabank.apibank.api.exceptionHandler.commonStandardError.StandardError;

@Tag(name = "Wallet", description = "Wallet related operations")
public interface WalletSwagger {

    @Operation(description = "Operation to search for the wallet by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found successfully",
                    content = @Content(schema = @Schema(implementation = WalletResponse.class))),
            @ApiResponse(responseCode = "400", description = "Wallet not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<WalletResponse> findById(@PathVariable("id") final String id);

    @Operation(description = "Operation to deposit balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deposit made successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wallet not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "The deposit amount must be greater than 0",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<Void> processDeposit(@RequestBody @Valid final BalanceOperationRequest request,
                                        @PathVariable("walletId") final String walletId);

    @Operation(description = "Operation to create a wallet for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wallet created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "The user id entered was not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "406", description = "This user already has a wallet",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<WalletResponse> createWalletForUser(@PathVariable("userId") final String userId);
}
