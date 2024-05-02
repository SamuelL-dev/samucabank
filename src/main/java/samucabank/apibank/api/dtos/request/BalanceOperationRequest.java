package samucabank.apibank.api.dtos.request;

import jakarta.validation.constraints.NotNull;

public record BalanceOperationRequest(@NotNull Integer amount) {}
