package samucabank.apibank.api.dtos.request;

import jakarta.validation.constraints.NotNull;
import samucabank.apibank.domain.enuns.card.CardOperationType;

public record CardTransactionRequest(@NotNull Integer amount, @NotNull CardOperationType type) {}
