package com.livraison.model.enums;

/**
 * Types de notifications du système
 */
public enum TypeNotification {
    NOUVELLE_LIVRAISON("Nouvelle livraison disponible",
            "Une nouvelle livraison correspondant à vos critères est disponible"),
    STATUT_LIVRAISON("Statut de livraison mis à jour",
            "Le statut de votre livraison a changé"),
    MESSAGE("Nouveau message",
            "Vous avez reçu un nouveau message"),
    PAIEMENT("Notification de paiement",
            "Statut de paiement mis à jour"),
    PROMOTION("Offre promotionnelle",
            "Nouvelle offre disponible"),
    URGENT("Notification urgente",
            "Message urgent de l'équipe Livraison");

    private final String titre;
    private final String messageParDefaut;

    TypeNotification(String titre, String messageParDefaut) {
        this.titre = titre;
        this.messageParDefaut = messageParDefaut;
    }

    // Getters
    public String getTitre() {
        return titre;
    }

    public String getMessageParDefaut() {
        return messageParDefaut;
    }
}