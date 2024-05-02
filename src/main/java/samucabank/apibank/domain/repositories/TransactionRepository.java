package samucabank.apibank.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import samucabank.apibank.domain.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findTop10BySenderIdOrderByTransactionDateDesc(@Param("id") String id);

    List<Transaction> findTop10ByReceiverIdOrderByTransactionDateDesc(@Param("id") String id);

}
