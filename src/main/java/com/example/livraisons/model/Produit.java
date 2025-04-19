package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import com.example.livraisons.model.enums.TypeProduit;

/**
 * Classe embarquée représentant un produit à livrer
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La taille est obligatoire")
    private TypeProduit taille;

    @Positive(message = "Le poids doit être positif")
    @DecimalMax(value = "50.0", message = "Le poids ne peut excéder 50 kg")
    private Double poids; // en kg

    private boolean fragile = false;

    @Size(max = 255, message = "L'URL de la photo ne doit pas dépasser 255 caractères")
    private String photoUrl;

    @Size(max = 100, message = "La catégorie ne doit pas dépasser 100 caractères")
    private String categorie;

    /**
     * Vérifie si le produit est volumineux
     */
    public boolean estVolumineux() {
        return taille == TypeProduit.GRAND;
    }

    /**
     * Vérifie si le produit est fragile
     */

    public boolean estFragile() {
        return fragile;
    }

    /**
     * Vérifie si le produit est valide pour la livraison
     */

    public boolean estValide() {
        return description != null && !description.isBlank() && poids > 0;
    }

}