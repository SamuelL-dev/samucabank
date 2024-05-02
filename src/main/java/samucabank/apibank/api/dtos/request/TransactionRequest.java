package samucabank.apibank.api.dtos.request;

import jakarta.validation.constraints.*;

public record TransactionRequest(

        @NotBlank String senderId,

        @NotBlank String receiverId,

        @NotNull @PositiveOrZero Integer amount,

        String description
){}
