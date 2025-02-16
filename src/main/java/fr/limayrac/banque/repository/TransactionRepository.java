package fr.limayrac.banque.repository;

import fr.limayrac.banque.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCompteEmetteurIdOrCompteDestinataireId(Long compteEmetteurId, Long compteDestinataireId);
}
