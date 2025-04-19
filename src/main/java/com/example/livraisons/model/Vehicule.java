package com.example.livraisons.model;

import com.example.livraisons.model.enums.TypeVehicule;

import jakarta.persistence.*;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Classe embarquée représentant un véhicule de livreur
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {

    @Enumerated(EnumType.STRING) // Type de l'énumération pour la base de données
    @NotNull(message = "le type de véhicule est obligatoire")
    private TypeVehicule type; // Type de véhicule (voiture, moto, vélo)

    @NotBlank(message = "l'immatriculation est obligatoire")
    @Pattern(regexp = "^[A-Z0-9]{5,15}$", message = "l'immatriculation doit contenir entre 5 et 15 caractères alphanumériques")
    private String immatriculation; // Immatriculation du véhicule

    @NotBlank(message = "le modele est obligatoire")
    @Size(min = 3, max = 100, message = "le modèle doit contenir entre 3 et 100 caractères")
    private String modele; // Modèle du véhicule

    @PositiveOrZero(message = "l'annee doit être positive ")
    @Digits(integer = 4, fraction = 0, message = "Annee Invalide")
    private int annee; // Année de fabrication du véhicule

    @NotBlank(message = "la couleur est obligatoire")
    @Size(min = 3, max = 50, message = "la couleur doit contenir entre 3 et 50 caractères")

    private String couleur; // Couleur du véhicule

    /**
     * Vérifie si le véhicule est valide pour la livraison
     */

    public boolean estValide() {

        return immatriculation != null && !immatriculation.isBlank();
    }

    /**
     * Vérifie si le véhicule est disponible pour la livraison
     */

    public boolean estDisponible() {
        return true; // Logique de disponibilité à implémenter
    }

}
