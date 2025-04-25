package samucabank.apibank.api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samucabank.apibank.api.config.swagger.documentation.CardSwagger;
import samucabank.apibank.api.dtos.request.CardTransactionRequest;
import samucabank.apibank.api.dtos.response.CardResponse;
import samucabank.apibank.domain.service.serviceAction.CardService;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController implements CardSwagger {

    private final CardService cardService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> findById(@PathVariable("id") final String id) {
        final CardResponse card = cardService.findByIdDto(id);
        return ResponseEntity.ok(card);
    }

    @Override
    @PostMapping("/users/{userId}")
    public ResponseEntity<CardResponse> createCardForUser(@PathVariable("userId") final String userId) {
        cardService.save(userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PostMapping("/{id}")
    public ResponseEntity<CardResponse> createCardTransaction(@PathVariable("id") final String id,
                                                              @RequestBody @Valid final CardTransactionRequest request) {
        cardService.createTransaction(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final String id) {
        cardService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
