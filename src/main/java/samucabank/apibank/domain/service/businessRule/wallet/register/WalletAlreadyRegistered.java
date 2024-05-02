package samucabank.apibank.domain.service.businessRule.wallet.register;

import org.springframework.stereotype.Component;
import samucabank.apibank.domain.service.customException.wallet.WalletAlreadyRegisteredException;

@Component
public class WalletAlreadyRegistered implements RegisterWalletValidator {
    @Override
    public void validate(final RegisterWalletArgs args) {
        if(args.user().getWallet() != null) {
            throw new WalletAlreadyRegisteredException("This user already has a wallet.");
        }
    }
}
