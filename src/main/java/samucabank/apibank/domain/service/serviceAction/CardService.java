package samucabank.apibank.domain.service.serviceAction;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import samucabank.apibank.api.dtos.request.CardTransactionRequest;
import samucabank.apibank.api.dtos.response.CardResponse;
import samucabank.apibank.domain.service.businessRule.card.register.RegisterCardArgs;
import samucabank.apibank.domain.service.businessRule.card.register.RegisterCardValidator;
import samucabank.apibank.infrastructure.card.CardDataGenerator;
import samucabank.apibank.domain.enums.card.CardFlag;
import samucabank.apibank.domain.enums.card.CardOperationType;
import samucabank.apibank.domain.enums.card.CardType;
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

    private final List<RegisterCardValidator> registerCardValidators;

    public Card findById(final String id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
    }

    public CardResponse findByIdDto(final String id) {
        Card card = findById(id);
        return mapper.map(card, CardResponse.class);
    }

    @Transactional
    public CardResponse save(final String userId) {
        final User user = userService.findById(userId);

        registerCardValidators.forEach(it -> it.validate(
                new RegisterCardArgs(
                        user
                )));

        final Card card = this.createForUser(user);

        cardEligibilityValidator.forEach(it -> it.checkEligibility(
                new CardEligibilityArgs(
                        user,
                        card
                )));

        cardLimitManager.adjustCardLimitBasedOnUserScore(card, user.getScore());

        return mapper.map(cardRepository.save(card), CardResponse.class);
    }

    @Transactional
    public CardResponse createCardTransaction(final String cardId, final CardTransactionRequest data) {
        final Card card = this.findById(cardId);

        final Wallet wallet = card.getUser().getWallet();

        final CardOperationType operationType = data.type();

        cardTransactionValidator.stream()
                .filter(it -> it.getType() == operationType)
                .forEach(it -> it.validate(new CardTransactionArgs(
                        data,
                        card,
                        wallet
                )));

        cardOperation.stream()
                .filter(it -> it.getType() == operationType)
                .forEach(o -> o.applyCardTransactionOperation
                        (new CardOperationArgs(
                                data,
                                card,
                                wallet
                        )));

        return mapper.map(cardRepository.save(card), CardResponse.class);
    }

    @Transactional
    public void delete(final String id) {
        final Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));

        deleteCardValidator.forEach(it -> it.validate(new DeleteCardArgs(card)));

        cardRepository.delete(card);
    }

    private Card createForUser(final User user) {
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

