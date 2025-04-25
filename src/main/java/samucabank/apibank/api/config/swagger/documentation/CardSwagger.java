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
import samucabank.apibank.api.dtos.request.CardTransactionRequest;
import samucabank.apibank.api.dtos.response.CardResponse;
import samucabank.apibank.api.exceptionHandler.commonStandardError.StandardError;

@Tag(name = "Card", description = "Card related operations")
public interface CardSwagger {

    @Operation(description = "Operation to search the card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card found successfully",
                    content = @Content(schema = @Schema(implementation = CardResponse.class))),
            @ApiResponse(responseCode = "400", description = "Card not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<CardResponse> findById(@PathVariable("id") final String id);

    @Operation(description = "Operation to create a card for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "The user id entered was not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "406", description = "This user already has a card",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<CardResponse> createCardForUser(@PathVariable("userId") final String userId);

    @Operation(description = "Operation to create a card transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card transaction created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "The card limit is insufficient to carry out the transaction",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "400", description = "Card id not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "The value must be greater than 0",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "422", description = "Insufficient wallet balance to complete the transaction",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<CardResponse> createTransaction(@PathVariable("id") final String id,
                                                   @RequestBody @Valid final CardTransactionRequest request);

    @Operation(description = "Operation to delete card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Card deleted sucessfully",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There is a pending transaction on the card",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "400", description = "Card id not found",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    ResponseEntity<Void> delete(@PathVariable("id") final String id);


}


