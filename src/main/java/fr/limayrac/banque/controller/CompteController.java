package fr.limayrac.banque.controller;

import fr.limayrac.banque.model.Compte;
import fr.limayrac.banque.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comptes")
public class CompteController {
    @Autowired
    private CompteService compteService;

    @GetMapping("/client/{clientId}")
    public List<Compte> getComptesByClient(@PathVariable Long clientId) {
        return compteService.getComptesByClient(clientId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compte> getCompteById(@PathVariable Long id) {
        Compte compte = compteService.getCompteById(id);
        return compte != null ? ResponseEntity.ok(compte) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Compte creerCompte(@RequestBody Compte compte) {
        if (compte.getClient() == null || compte.getClient().getId() == null) {
            throw new RuntimeException("Le client doit être associé au compte");
        }
        return compteService.creerCompte(compte);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCompte(@PathVariable Long id) {
        compteService.supprimerCompte(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{compteId}/decouvert")
    public ResponseEntity<Compte> modifierDecouvert(
            @PathVariable Long compteId,
            @RequestParam double nouveauDecouvert) {
        try {
            Compte compte = compteService.modifierDecouvert(compteId, nouveauDecouvert);
            return ResponseEntity.ok(compte);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
