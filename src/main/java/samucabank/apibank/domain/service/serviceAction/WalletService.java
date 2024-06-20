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

    private final WalletRepository walletRepository;

    private final UserService userService;

    private final List<RegisterWalletValidator> registerWalletValidators;

    private final List<CashFlowOperation> cashFlowOperations;

    private final ModelMapper mapper;

    public Wallet findById(final String id) {
        return walletRepository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id));
    }

    public WalletResponse findByIdDTO(final String id) {
        final Wallet wallet = findById(id);
        return mapper.map(wallet, WalletResponse.class);
    }


    @Transactional
    public WalletResponse save(final String userId) {
        final User user = userService.findById(userId);

        registerWalletValidators.forEach(v -> v.validate(new RegisterWalletArgs(user)));

        final Wallet wallet = this.createWallet(user);

        return mapper.map(walletRepository.save(wallet), WalletResponse.class);
    }

    @Transactional
    public void processDeposit(final String walletId, final BalanceOperationRequest request) {
        final Wallet wallet = findById(walletId);

        cashFlowOperations.stream()
                .forEach(it -> it.execute(
                        new CashFlowOperationArgs(
                                wallet,
                                request
                        )));
    }

    private Wallet createWallet(final User user) {
        return Wallet.builder()
                .balance(0)
                .currency(Currency.BRL)
                .user(user)
                .build();
    }
}
