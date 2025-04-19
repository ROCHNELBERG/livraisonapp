package com.example.livraisons.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;

/**
 * Classe embarquée représentant une adresse
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Adresse {
    @NotBlank(message = "le nom de l'adresse est obligatoire")
    @Size(min = 3, max = 1000, message = "le nom de l'adresse doit contenir entre 3 et 100 caractères")
    private String nom;

    @NotBlank(message = "le nom de la rue est obligatoire")
    @Size(min = 3, max = 255, message = "le nom de la rue doit contenir entre 3 et 255 caractères")
    private String rue;

    @NotBlank(message = "le nom de la ville est obligatoire")
    @Size(min = 3, max = 100, message = "le nom de la ville doit contenir entre 3 et 100 caractères")
    private String ville;

    @NotBlank(message = "le code postal est obligatoire")
    @Pattern(regexp = "^[0-9]{5}$", message = "le code postal doit contenir 5 chiffres")

    private String codePostal;

    @NotBlank(message = "le pays est obligatoire")
    @Size(min = 3, max = 100, message = "le pays ne doit pas depasser 100 caractères")
    private String pays;

    @Embedded
    @Valid
    private Coordonnees coordonnees; // Coordonnées GPS de l'adresse
    @Size(max = 255, message = " les instructions spéciales ne doivent pas dépasser 255 caractères")
    private String instructions; // Instructions spéciales pour la livraison

    public boolean estValide() {
        return rue != null && !rue.isBlank() &&
                ville != null && !ville.isBlank() &&
                codePostal != null && !codePostal.isBlank() &&
                pays != null && !pays.isBlank();
    }
}