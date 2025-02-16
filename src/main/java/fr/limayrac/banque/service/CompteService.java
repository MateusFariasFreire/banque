package fr.limayrac.banque.service;

import fr.limayrac.banque.model.Compte;
import fr.limayrac.banque.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompteService {
    @Autowired
    private CompteRepository compteRepository;

    public List<Compte> getComptesByClient(Long clientId) {
        return compteRepository.findByClientId(clientId);
    }

    public Compte getCompteById(Long id) {
        return compteRepository.findById(id).orElse(null);
    }

    public Compte creerCompte(Compte compte) {
        if (compte.getClient() == null || compte.getClient().getId() == null) {
            throw new RuntimeException("Le client doit être associé au compte");
        }

        return compteRepository.save(compte);
    }

    public void supprimerCompte(Long id) {
        compteRepository.deleteById(id);
    }

    public Compte modifierDecouvert(Long compteId, double nouveauDecouvert) {
        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));

        compte.setDecouvertAutorise(nouveauDecouvert);
        return compteRepository.save(compte);
    }
}
