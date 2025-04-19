package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Position {

    @Embedded
    @NotNull(message = "Les coordonnées sont obligatoires")
    private Coordonnees coordonnees;

    @NotNull(message = "La date/heure est obligatoire")
    @PastOrPresent(message = "La date/heure doit être dans le passé ou présent")
    private LocalDateTime dateHeure;

    @PositiveOrZero(message = "La précision doit être positive")
    private Double precisionMetres; // Précision en mètres

    @PositiveOrZero(message = "La vitesse doit être positive")
    private Double vitesseKmh; // Vitesse en km/h

    /**
     * Vérifie si la position est récente (moins de 5 minutes)
     */
    public boolean estRecente() {
        return dateHeure != null && dateHeure.isAfter(LocalDateTime.now().minusMinutes(5));
    }

    /**
     * Vérifie si la position est valide
     */
    public boolean estValide() {
        return coordonnees != null && coordonnees.estValide() &&
                dateHeure != null && precisionMetres != null &&
                vitesseKmh != null;
    }

    /**
     * Vérifie si la position est précise
     */
    public boolean estPrecise() {
        return precisionMetres != null && precisionMetres < 10; // Précision inférieure à 10 mètres
    }
}
