package com.example.livraisons.model.enums;

/**
 * Statut des documents de vérification
 */
public enum StatutDocument {
    EN_ATTENTE("En attente de vérification"),
    VERIFIE("Document vérifié et approuvé"),
    REJETE("Document rejeté"),
    EXPIRED("Document expiré"),
    EN_COURS("Vérification en cours");

    private final String description;

    StatutDocument(String description) {
        this.description = description;
    }

    // Getter
    public String getDescription() {
        return description;
    }

    /**
     * Vérifie si le document est valide
     */
    public boolean estValide() {
        return this == VERIFIE;
    }
}