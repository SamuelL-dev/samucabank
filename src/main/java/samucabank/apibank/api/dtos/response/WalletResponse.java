package samucabank.apibank.api.dtos.response;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samucabank.apibank.domain.enuns.wallet.Currency;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"currency", "balance", "userId"})
public class WalletResponse {

    private Currency currency;

    private Integer balance;

    private String userId;
}