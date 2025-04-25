package samucabank.apibank.domain.service.serviceAction;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import samucabank.apibank.api.dtos.request.BalanceOperationRequest;
import samucabank.apibank.api.dtos.response.WalletResponse;
import samucabank.apibank.domain.enums.wallet.Currency;
import samucabank.apibank.domain.model.User;
import samucabank.apibank.domain.model.Wallet;
import samucabank.apibank.domain.repositories.WalletRepository;
import samucabank.apibank.domain.service.businessRule.wallet.register.RegisterWalletArgs;
import samucabank.apibank.domain.service.businessRule.wallet.register.RegisterWalletValidator;
import samucabank.apibank.domain.service.customException.wallet.WalletNotFoundException;
import samucabank.apibank.domain.service.operations.wallet.CashFlowOperation;
import samucabank.apibank.domain.service.operations.wallet.CashFlowOperationArgs;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repository;

    private final UserService userService;

    private final List<RegisterWalletValidator> registerValidators;

    private final List<CashFlowOperation> cashFlowOperations;

    private final ModelMapper mapper;

    public Wallet findById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new WalletNotFoundException(id));
    }

    public WalletResponse findByIdDTO(String id) {
        final Wallet wallet = findById(id);
        return mapper.map(wallet, WalletResponse.class);
    }


    @Transactional
    public WalletResponse save(String userId) {
        final User user = userService.findById(userId);

        registerValidators.forEach(v -> v.validate(
            new RegisterWalletArgs(user)
        ));

        final Wallet wallet = this.createForUser(user);

        return mapper.map(repository.save(wallet), WalletResponse.class);
    }

    @Transactional
    public void processDeposit(
        String walletId,
        BalanceOperationRequest data
    ) {
        final Wallet wallet = findById(walletId);

        cashFlowOperations.forEach(it ->
            it.apply(
                new CashFlowOperationArgs(
                    wallet,
                    data
                )
            )
        );
    }

    private Wallet createForUser(User user) {
        return Wallet.builder()
            .balance(0)
            .currency(Currency.BRL)
            .user(user)
            .build();
    }
}
