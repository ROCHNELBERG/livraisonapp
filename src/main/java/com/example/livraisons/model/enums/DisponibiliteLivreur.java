package com.example.livraisons.model.enums;

/**
 * Statut de disponibilité des livreurs
 */
public enum DisponibiliteLivreur {
    DISPONIBLE( // Accepte de nouvelles livraisons
            true, // Visible dans les recherches
            "Disponible immédiatement"),
    OCCUPE( // En cours de livraison
            false, // Non visible
            "Actuellement en livraison"),
    EN_PAUSE( // Pause temporaire
            false, // Non visible
            "En pause"),
    HORS_LIGNE( // Fin de service
            false, // Non visible
            "Hors ligne"),
    EN_MISSION( // Accepté une livraison mais pas encore commencé
            false, // Non visible
            "En attente de livraison"),
    EN_ATTENTE( // En attente de validation
            false, // Non visible
            "En attente de validation");

    private final boolean disponible;
    private final String description;

    DisponibiliteLivreur(boolean disponible, String description) {
        this.disponible = disponible;
        this.description = description;
    }

    // Getters
    public boolean isDisponible() {
        return disponible;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Vérifie si le livreur peut accepter une nouvelle livraison
     */
    public boolean peutAccepterLivraison() {
        return this == DISPONIBLE;
    }
}