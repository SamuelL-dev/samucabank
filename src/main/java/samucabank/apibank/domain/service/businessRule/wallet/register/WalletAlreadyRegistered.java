package samucabank.apibank.domain.service.businessRule.wallet.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.model.User;

import java.util.Optional;

@Component
public class WalletAlreadyRegistered implements RegisterWalletValidator {
    @Override
    public void validate(RegisterWalletArgs args) {

        Optional.ofNullable(args)
            .map(RegisterWalletArgs::user)
            .map(User::getWallet)
            .orElseThrow(() -> new IllegalArgumentException("This user already has a wallet"));
    }
}
