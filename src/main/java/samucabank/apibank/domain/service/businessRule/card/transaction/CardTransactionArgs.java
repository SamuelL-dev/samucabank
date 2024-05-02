package samucabank.apibank.domain.service.businessRule.card.transaction;

import samucabank.apibank.api.dtos.request.CardTransactionRequest;
import samucabank.apibank.domain.model.Card;
import samucabank.apibank.domain.model.Wallet;

public record CardTransactionArgs(CardTransactionRequest request, Card card, Wallet wallet){}
