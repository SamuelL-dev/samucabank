package samucabank.apibank.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samucabank.apibank.api.dtos.request.CardTransactionRequest;
import samucabank.apibank.api.dtos.response.CardResponse;
import samucabank.apibank.domain.service.serviceAction.CardService;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{id}")
    @Operation(description = "Operation to search the card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card found successfully"),
            @ApiResponse(responseCode = "400", description = "Card not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CardResponse> findById(@PathVariable("id") final String id) {
        final CardResponse card = cardService.findByIdDto(id);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/users/{userId}")
    @Operation(description = "Operation to create a card for user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card created successfully"),
            @ApiResponse(responseCode = "400", description = "The user id entered was not found"),
            @ApiResponse(responseCode = "406", description = "This user already has a card"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CardResponse> createCardForUser(@PathVariable("userId") final String userId) {
        cardService.save(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}")
    @Operation(description = "Operation to create a card transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card transaction created successfully"),
            @ApiResponse(responseCode = "422", description = "The card limit is insufficient to carry out the transaction"),
            @ApiResponse(responseCode = "400", description = "Card id not found"),
            @ApiResponse(responseCode = "422", description = "The value must be greater than 0"),
            @ApiResponse(responseCode = "422", description = "Insufficient wallet balance to complete the transaction"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CardResponse> createCardTransaction(@PathVariable("id") final String id,
                                                              @RequestBody @Valid final CardTransactionRequest request) {
        cardService.createCardTransaction(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Operation to delete card by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Card deleted sucessfully"),
            @ApiResponse(responseCode = "409", description = "There is a pending transaction on the card"),
            @ApiResponse(responseCode = "400", description = "Card id not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(@PathVariable("id") final String id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
