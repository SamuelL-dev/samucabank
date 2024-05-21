package samucabank.apibank.domain.model;

import jakarta.persistence.*;
import lombok.*;
import samucabank.apibank.domain.enums.card.CardFlag;
import samucabank.apibank.domain.enums.card.CardType;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(nullable = false)
    private String cardNumber;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private String cvv;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(nullable = false)
    private Integer cardLimit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardFlag cardFlag;

    private int amountSpent;

    @Builder
    public Card(String cardNumber, User user, LocalDate expirationDate, String cvv, CardType cardType, CardFlag cardFlag) {
        this.cardNumber = cardNumber;
        this.user = user;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.cardType = cardType;
        this.cardFlag = cardFlag;
    }
}
