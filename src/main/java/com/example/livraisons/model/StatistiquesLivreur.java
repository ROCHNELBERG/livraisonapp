package com.example.livraisons.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Classe embarquée pour les statistiques des livreurs
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatistiquesLivreur {

    @PositiveOrZero(message = "Le nombre de livraisons ne peut pas être négatif")
    private Integer livraisonsCompletees = 0;

    @PositiveOrZero(message = "La distance parcourue ne peut pas être négative")
    private Double distanceParcourue = 0.0; // en km

    @PositiveOrZero(message = "Le temps total ne peut pas être négatif")
    private Double tempsTotal = 0.0; // en heures

    @PositiveOrZero(message = "Le taux de réussite ne peut pas être négatif")
    @DecimalMax(value = "100.0", message = "Le taux de réussite ne peut excéder 100%")
    private Double tauxReussite = 0.0;

    /**
     * Met à jour les statistiques après une livraison réussie
     */
    public void ajouterLivraisonReussie(double distance, double duree) {
        this.livraisonsCompletees++;
        this.distanceParcourue += distance;
        this.tempsTotal += duree;
        this.tauxReussite = (livraisonsCompletees / (livraisonsCompletees + 0.0)) * 100;
    }

    /**
     * Met à jour les statistiques après une livraison échouée
     */

    public void ajouterLivraisonEchouee() {
        this.tauxReussite = (livraisonsCompletees / (livraisonsCompletees + 0.0)) * 100;
    }

}
