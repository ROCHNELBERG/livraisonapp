package com.example.livraisons.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;
import com.example.livraisons.model.enums.TypeConfirmation;

/**
 * Classe embarquée représentant des coordonnées géographiques
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Coordonnees {
    @NotNull(message = "La latitude est obligatoire")
    @DecimalMin(value = "-90.0", message = "La latitude doit être comprise entre -90 et 90")
    @DecimalMax(value = "90.0", message = "La latitude doit être comprise entre -90 et 90")
    private Double latitude; // Latitude de la coordonnée

    @NotNull(message = "La longitude est obligatoire")
    @DecimalMin(value = "-180.0", message = "La longitude doit être comprise entre -180 et 180")
    @DecimalMax(value = "180.0", message = "La longitude doit être comprise entre -180 et 180")
    private Double longitude; // Longitude de la coordonnée

    /**
     * Vérifie si les coordonnées son t valides
     */

    public boolean estValide() {
        return latitude != null && longitude != null && latitude >= -90 && latitude <= 90 && longitude >= -180
                && longitude <= 180;
    }

}