package samucabank.apibank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import samucabank.apibank.domain.enums.card.CardFlag;
import samucabank.apibank.domain.enums.card.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Cards")
public class Card {

    @Id
    private final String id = UUID.randomUUID().toString();

    @Column(nullable = false) private String cardNumber;

    @OneToOne private User user;

    @Column(nullable = false) private LocalDate expirationDate;

    @Column(nullable = false) private final String cvv;

    @Column(nullable = false) @Enumerated(EnumType.STRING) private CardType type;

    @Column(nullable = false) private BigDecimal limit;

    @Column(nullable = false) @Enumerated(EnumType.STRING) private CardFlag flag;

    private int amountSpent;

    @Builder
    public Card(
        String cardNumber,
        User user,
        LocalDate expirationDate,
        String cvv,
        CardType type,
        CardFlag flag
    ) {
        this.cardNumber = cardNumber;
        this.user = user;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.type = type;
        this.flag = flag;
    }
}
