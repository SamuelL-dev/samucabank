package samucabank.apibank.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samucabank.apibank.domain.model.Card;


@Repository
public interface CardRepository extends JpaRepository<Card, String> {
}
