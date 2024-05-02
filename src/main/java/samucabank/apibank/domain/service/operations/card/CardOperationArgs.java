package samucabank.apibank.domain.service.operations.card;

import samucabank.apibank.api.dtos.request.CardTransactionRequest;
import samucabank.apibank.domain.model.Card;
import samucabank.apibank.domain.model.Wallet;


public record CardOperationArgs(
        CardTransactionRequest data,
        Card card,
        Wallet wallet

) {
}
