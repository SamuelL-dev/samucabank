package samucabank.apibank.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samucabank.apibank.domain.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
}
