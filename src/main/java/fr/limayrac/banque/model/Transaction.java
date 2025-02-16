package fr.limayrac.banque.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double montant;
    private LocalDateTime dateTransaction;
    private boolean autorise;

    @ManyToOne
    @JoinColumn(name = "compte_emetteur_id")
    @JsonIgnoreProperties({"client", "transactions"})
    private Compte compteEmetteur;

    @ManyToOne
    @JoinColumn(name = "compte_destinataire_id")
    @JsonIgnoreProperties({"client", "transactions"})
    private Compte compteDestinataire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public boolean isAutorise() {
        return autorise;
    }

    public void setAutorise(boolean autorise) {
        this.autorise = autorise;
    }

    public Compte getCompteEmetteur() {
        return compteEmetteur;
    }

    public void setCompteEmetteur(Compte compteEmetteur) {
        this.compteEmetteur = compteEmetteur;
    }

    public Compte getCompteDestinataire() {
        return compteDestinataire;
    }

    public void setCompteDestinataire(Compte compteDestinataire) {
        this.compteDestinataire = compteDestinataire;
    }
}
