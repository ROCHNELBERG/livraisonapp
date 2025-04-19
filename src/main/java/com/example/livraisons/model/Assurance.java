package com.example.livraisons.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Classe embarquée pour les informations d'assurance
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assurance {

    private boolean active = false;

    @PositiveOrZero(message = "La valeur déclarée ne peut pas être négative")
    @DecimalMax(value = "10000.0", message = "La valeur déclarée ne peut excéder 10 000")
    private Double valeurDeclaree = 0.0;

    @PositiveOrZero(message = "La prime ne peut pas être négative")
    @DecimalMax(value = "500.0", message = "La prime ne peut excéder 500")
    private Double prime = 0.0;

    /**
     * Calcule la prime en fonction de la valeur déclarée
     */
    public void calculerPrime() {
        if (active && valeurDeclaree > 0) {
            this.prime = valeurDeclaree * 0.02; // 2% de la valeur déclarée
        }
    }

    /**
     * Vérifie si l'assurance est valide
     */

    public boolean estValide() {
        return active && valeurDeclaree > 0 && prime > 0;
    }

    /**
     * Vérifie si l'assurance est active
     */
    public boolean estActive() {
        return active;

    }
}