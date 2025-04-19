package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;
import com.example.livraisons.model.enums.DisponibiliteLivreur;
import com.example.livraisons.model.enums.StatutDocument;

/**
 * Classe représentant un livreur de l'application.
 * Hérite de la classe Utilisateur.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livreurs")
public class Livreur extends Utilisateur {

    @Embedded
    @Valid
    private Vehicule vehicule;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DisponibiliteLivreur disponibilite = DisponibiliteLivreur.HORS_LIGNE;

    @Embedded
    @Valid
    private Position position;

    @Column(name = "Rayon_livraison")
    @Min(value = 0, message = "Le rayon de livraison ne peut pas être négatif")
    @DecimalMin(value = "1.0", message = "Le rayon de livraison doit être d'au moins 1 km")
    @DecimalMax(value = "50.0", message = "Le rayon de livraison ne peut pas dépasser 100 km")
    private double rayonLivraison;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "livreur_id")
    @Builder.Default
    private List<@Valid DocumentVerification> documentsVerifications = new ArrayList<>();

    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();
    @Embedded
    @Valid
    private StatistiquesLivreur statistiques;

    /**
     * Vérifie si le livreur est disponible
     */
    public boolean estDisponible() {
        return disponibilite == DisponibiliteLivreur.DISPONIBLE
                && documentsVerifications.stream()
                        .allMatch(doc -> doc.getStatut() == StatutDocument.VERIFIE);
    }

    /**
     * Ajoute un document de vérification
     */
    public void ajouterDocument(DocumentVerification document) {
        if (document != null) {
            documentsVerifications.add(document);
            document.setLivreur(this);
        }
    }

    /**
     * Vérifie si le livreur est en ligne
     */
    public boolean estEnLigne() {
        return disponibilite == DisponibiliteLivreur.DISPONIBLE;
    }

    /**
     * Vérifie si le livreur est hors ligne
     */
    public boolean estHorsLigne() {
        return disponibilite == DisponibiliteLivreur.HORS_LIGNE;
    }

    /**
     * Vérifie si le livreur est en pause
     */
    public boolean estEnPause() {
        return disponibilite == DisponibiliteLivreur.EN_PAUSE;
    }

    /**
     * Vérifie si le livreur est en attente de validation
     */
    public boolean estEnAttente() {
        return disponibilite == DisponibiliteLivreur.EN_ATTENTE;
    }
}