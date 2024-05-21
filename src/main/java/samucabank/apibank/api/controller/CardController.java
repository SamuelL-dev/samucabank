package samucabank.apibank.api.controller;


import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Find card by ID", description = "Find a card by its ID", method = "GET")
    public ResponseEntity<CardResponse> findById(@PathVariable("id") final String id) {
        final CardResponse card = cardService.findByIdDto(id);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/users/{userId}")
    @Operation(summary = "Create card by user ID", description = "Creating a new card", method = "POST")
    public ResponseEntity<CardResponse> createCardForUser(@PathVariable("userId") final String userId) {
        this.cardService.save(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{id}")
    @Operation(summary = "Create card transaction", description = "Creating a new card transaction", method = "POST")
    public ResponseEntity<CardResponse> createCardTransaction(@PathVariable("id") final String id,
                                                              @RequestBody @Valid final CardTransactionRequest request) {
        this.cardService.createCardTransaction(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete card by ID", description = "Delete a card by its ID", method = "DELETE")
    public ResponseEntity<Void> delete(@PathVariable("id") final String id) {
        this.cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
