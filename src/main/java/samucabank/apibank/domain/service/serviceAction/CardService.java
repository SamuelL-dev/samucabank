package samucabank.apibank.domain.service.serviceAction;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import samucabank.apibank.api.dtos.request.CardTransactionRequest;
import samucabank.apibank.api.dtos.response.CardResponse;
import samucabank.apibank.infrastructure.card.CardDataGenerator;
import samucabank.apibank.domain.enuns.card.CardFlag;
import samucabank.apibank.domain.enuns.card.CardOperationType;
import samucabank.apibank.domain.enuns.card.CardType;
import samucabank.apibank.domain.model.Card;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.model.Wallet;
import samucabank.apibank.domain.repositories.CardRepository;
import samucabank.apibank.domain.service.businessRule.card.checkEligibility.CardEligibilityArgs;
import samucabank.apibank.domain.service.businessRule.card.checkEligibility.CardEligibilityValidator;
import samucabank.apibank.domain.service.businessRule.card.delete.DeleteCardArgs;
import samucabank.apibank.domain.service.businessRule.card.delete.DeleteCardValidator;
import samucabank.apibank.domain.service.businessRule.card.transaction.CardTransactionArgs;
import samucabank.apibank.domain.service.businessRule.card.transaction.CardTransactionValidator;
import samucabank.apibank.domain.service.customException.card.CardNotFoundException;
import samucabank.apibank.domain.service.operations.card.CardOperation;
import samucabank.apibank.domain.service.operations.card.CardOperationArgs;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;


    private final List<CardEligibilityValidator> cardEligibilityValidator;

    private final List<DeleteCardValidator> deleteCardValidator;

    private final List<CardTransactionValidator> cardTransactionValidator;

    private final List<CardOperation> cardOperation;

    private final CardDataGenerator cardDataGenerator;

    private final UserService userService;

    private final ModelMapper mapper;

    private final CardLimitAdjustmentService cardLimitManager;

    public Card findById(final String id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
    }

    public CardResponse findByIdDto(final String id) {
        Card card = findById(id);
        return mapper.map(card, CardResponse.class);
    }

    @Transactional
    public CardResponse register(final String userId) {
        final User user = userService.findById(userId);

        final Card card = this.createNewCard(user);

        this.cardEligibilityValidator.forEach(it -> it.checkEligibility(new CardEligibilityArgs(user, card)));

        this.cardLimitManager.adjustCardLimitBasedOnUserScore(card, user.getScore());
        user.setCard(card);

        return mapper.map(this.cardRepository.save(card), CardResponse.class);
    }

    @Transactional
    public CardResponse createCardTransaction(final String cardId, final CardTransactionRequest data) {
        Card card = this.findById(cardId);

        Wallet wallet = card.getUser().getWallet();

        CardOperationType operationType = data.type();

        this.cardTransactionValidator.stream()
                .filter(it -> it.getType() == operationType)
                .forEach(it -> it.validate(new CardTransactionArgs(
                        data,
                        card,
                        wallet
                )));

        this.cardOperation.stream()
                .filter(it -> it.getType() == operationType)
                .forEach(o -> o.applyCardTransactionOperation
                        (new CardOperationArgs(
                                data,
                                card,
                                wallet
                        )));

        return mapper.map(this.cardRepository.save(card), CardResponse.class);
    }

    @Transactional
    public void delete(final String id) {
        final Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));

        this.deleteCardValidator.forEach(it -> it.validate(new DeleteCardArgs(card)));

        this.cardRepository.delete(card);
    }

    private Card createNewCard(final User user) {
        return Card.builder()
                .cardNumber(cardDataGenerator.generateCardNumber())
                .cvv(cardDataGenerator.generateCVV())
                .expirationDate(cardDataGenerator.generateExpirationDate())
                .user(user)
                .cardType(CardType.CREDIT_DEBIT)
                .cardFlag(CardFlag.MASTERCARD)
                .build();
    }
}

