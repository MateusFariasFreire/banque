package fr.limayrac.banque.controller;

import fr.limayrac.banque.model.Transaction;
import fr.limayrac.banque.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/compte/{compteId}")
    public List<Transaction> getTransactionsByCompte(@PathVariable Long compteId) {
        return transactionService.getTransactionsByCompte(compteId);
    }

    @PostMapping("/virement")
    public ResponseEntity<Transaction> effectuerVirement(
            @RequestParam Long compteEmetteurId,
            @RequestParam Long compteDestinataireId,
            @RequestParam double montant) {
        try {
            Transaction transaction = transactionService.effectuerVirement(compteEmetteurId, compteDestinataireId, montant);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/autoriser/{transactionId}")
    public ResponseEntity<Transaction> autoriserVirement(@PathVariable Long transactionId) {
        try {
            Transaction transaction = transactionService.autoriserVirement(transactionId);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/retrait")
    public ResponseEntity<Transaction> effectuerRetrait(
            @RequestParam Long compteId,
            @RequestParam double montant) {
        try {
            Transaction transaction = transactionService.effectuerRetrait(compteId, montant);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
