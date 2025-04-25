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

    private final CardRepository repository;

    private final List<CardEligibilityValidator> eligibilityValidators;

    private final List<DeleteCardValidator> deleteValidators;

    private final List<CardTransactionValidator> transactionValidators;

    private final List<CardOperation> operations;

    private final CardDataGenerator cardDataGenerator;

    private final UserService userService;

    private final ModelMapper mapper;

    private final CardLimitAdjustmentService limitManager;

    private final List<RegisterCardValidator> registerValidators;

    public Card findById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new CardNotFoundException(id));
    }

    public CardResponse findByIdDto(String id) {
        final Card card = findById(id);
        return mapper.map(card, CardResponse.class);
    }

    @Transactional
    public void save(String userId) {
        final User user = userService.findById(userId);

        registerValidators.forEach(it -> it.validate(
            new RegisterCardArgs(user))
        );

        final Card card = this.createForUser(user);

        eligibilityValidators.forEach(it -> it.checkEligibility(
            new CardEligibilityArgs(
                user,
                card
            )
        ));

        limitManager.adjustCardLimitBasedOnUserScore(card, user.getScore());

        mapper.map(repository.save(card), CardResponse.class);
    }

    @Transactional
    public void createTransaction(final String cardId, final CardTransactionRequest data) {
        final Card card = this.findById(cardId);

        final Wallet wallet = card.getUser().getWallet();

        final CardOperationType type = data.type();

        transactionValidators.stream()
            .filter(it -> it.getType() == type)
            .forEach(it -> it.validate(
                new CardTransactionArgs(
                    data,
                    card,
                    wallet
                )
            ));

        operations.stream()
            .filter(it -> it.getType() == type)
            .forEach(it -> it.apply(
                new CardOperationArgs(
                    data,
                    card,
                    wallet
                )
            ));

        mapper.map(repository.save(card), CardResponse.class);
    }

    @Transactional
    public void delete(final String id) {
        final Card card = repository.findById(id)
            .orElseThrow(() -> new CardNotFoundException(id));

        deleteValidators.forEach(it -> it.validate(
            new DeleteCardArgs(card)
        ));

        repository.delete(card);
    }

    private Card createForUser(User user) {
        return Card.builder()
            .cardNumber(cardDataGenerator.generateCardNumber())
            .cvv(cardDataGenerator.generateCVV())
            .expirationDate(cardDataGenerator.generateExpirationDate())
            .user(user)
            .type(CardType.CREDIT_DEBIT)
            .flag(CardFlag.MASTERCARD)
            .build();
    }
}

