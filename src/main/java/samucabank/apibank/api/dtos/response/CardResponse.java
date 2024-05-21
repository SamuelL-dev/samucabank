package samucabank.apibank.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samucabank.apibank.domain.enums.card.CardFlag;
import samucabank.apibank.domain.enums.card.CardType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"cardNumber", "cardLimit", "cvv", "expirationDate", "cardType", "cardFlag", "status", "userId"})
public class CardResponse {

    private String cardNumber;

    private String userId;

    private Integer cardLimit;

    private String cvv;

    private LocalDate expirationDate;

    private CardType cardType;

    private CardFlag cardFlag;

}
