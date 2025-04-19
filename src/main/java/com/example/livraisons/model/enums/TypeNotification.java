package com.livraison.model.enums;

/**
 * Types de notifications disponibles dans le système
 */
public enum TypeNotification {
    NOUVELLE_LIVRAISON("Nouvelle livraison disponible"),
    STATUT_LIVRAISON("Changement de statut de livraison"),
    MESSAGE("Nouveau message reçu"),
    PAIEMENT("Notification de paiement"),
    PROMOTION("Offre promotionnelle"),
    URGENTE("Notification urgente"),
    SYSTEME("Notification système");

    private final String description;

    TypeNotification(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Vérifie si ce type de notification est critique
     */
    public boolean isCritique() {
        return this == URGENTE || this == SYSTEME;
    }
}