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
import samucabank.apibank.api.dtos.request.TransactionRequest;
import samucabank.apibank.api.dtos.response.StatementDetailsResponse;
import samucabank.apibank.api.dtos.response.TransactionResponse;
import samucabank.apibank.api.exceptionHandler.commonStandardError.StandardError;

@Tag(name = "Transaction", description = "Transaction related operations")
public interface TransactionSwagger {

    @Operation(description = "Operation to find the statement by wallet ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statement found successfully",
                    content = @Content(schema = @Schema(implementation = StatementDetailsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Wallet id not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<StatementDetailsResponse> getStatementByWalletId(@PathVariable("walletId") final String walletId);

    @Operation(description = "Operation to create a transaction for a wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Insufficient balance to complete the transaction",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "400", description = "Wallet id not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Transaction value must be greater than 0",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal error server",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<TransactionResponse> createTransactionForWallet(@RequestBody @Valid final TransactionRequest request);
}
