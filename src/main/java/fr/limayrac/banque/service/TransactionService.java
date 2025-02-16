package fr.limayrac.banque.service;

import fr.limayrac.banque.model.Compte;
import fr.limayrac.banque.model.Transaction;
import fr.limayrac.banque.repository.CompteRepository;
import fr.limayrac.banque.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CompteRepository compteRepository;

    public List<Transaction> getTransactionsByCompte(Long compteId) {
        return transactionRepository.findByCompteEmetteurIdOrCompteDestinataireId(compteId, compteId);
    }

    public Transaction effectuerVirement(Long compteEmetteurId, Long compteDestinataireId, double montant) {
        Compte compteEmetteur = compteRepository.findById(compteEmetteurId)
                .orElseThrow(() -> new RuntimeException("Compte émetteur introuvable"));
        Compte compteDestinataire = compteRepository.findById(compteDestinataireId)
                .orElseThrow(() -> new RuntimeException("Compte destinataire introuvable"));

        boolean virementInterne = compteEmetteur.getClient().getId().equals(compteDestinataire.getClient().getId());

        if (compteEmetteur.getSolde() - montant < -compteEmetteur.getDecouvertAutorise()) {
            throw new RuntimeException("Solde insuffisant pour effectuer ce virement !");
        }

        Transaction transaction = new Transaction();
        transaction.setCompteEmetteur(compteEmetteur);
        transaction.setCompteDestinataire(compteDestinataire);
        transaction.setMontant(montant);
        transaction.setDateTransaction(LocalDateTime.now());

        if (virementInterne) {
            transaction.setAutorise(true);
            compteEmetteur.setSolde(compteEmetteur.getSolde() - montant);
            compteDestinataire.setSolde(compteDestinataire.getSolde() + montant);
            compteRepository.save(compteEmetteur);
            compteRepository.save(compteDestinataire);
        } else {
            transaction.setAutorise(false);
        }

        return transactionRepository.save(transaction);
    }

    public Transaction autoriserVirement(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction introuvable"));

        if (!transaction.isAutorise()) {
            transaction.setAutorise(true);
            Compte compteEmetteur = transaction.getCompteEmetteur();
            Compte compteDestinataire = transaction.getCompteDestinataire();

            if (compteEmetteur.getSolde() - transaction.getMontant() < -compteEmetteur.getDecouvertAutorise()) {
                throw new RuntimeException("Solde insuffisant après autorisation !");
            }

            // ✅ Appliquer la transaction
            compteEmetteur.setSolde(compteEmetteur.getSolde() - transaction.getMontant());
            compteDestinataire.setSolde(compteDestinataire.getSolde() + transaction.getMontant());
            compteRepository.save(compteEmetteur);
            compteRepository.save(compteDestinataire);
        }

        return transactionRepository.save(transaction);
    }

    public Transaction effectuerRetrait(Long compteId, double montant) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));

        if (compte.getSolde() - montant < -compte.getDecouvertAutorise()) {
            throw new RuntimeException("Solde insuffisant pour effectuer ce retrait !");
        }

        Transaction transaction = new Transaction();
        transaction.setCompteEmetteur(compte);
        transaction.setMontant(montant);
        transaction.setDateTransaction(LocalDateTime.now());
        transaction.setAutorise(true);

        compte.setSolde(compte.getSolde() - montant);
        compteRepository.save(compte);

        return transactionRepository.save(transaction);
    }
}
