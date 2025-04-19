package com.livraison.model.enums;

/**
 * Définit les canaux par lesquels les notifications peuvent être envoyées
 */
public enum CanalNotification {

    EMAIL("Email", true),
    SMS("SMS Textuel", true),
    APP("Application Mobile", false),
    PUSH("Notification Push", false),
    WHATSAPP("WhatsApp", true);

    private final String libelle;
    private final boolean externe; // Si le canal nécessite un service externe

    CanalNotification(String libelle, boolean externe) {
        this.libelle = libelle;
        this.externe = externe;
    }

    public String getLibelle() {
        return libelle;
    }

    public boolean isExterne() {
        return externe;
    }

    /**
     * Vérifie si le canal est disponible pour l'envoi immédiat
     */
    public boolean estDisponible() {
        if (this.externe) {
            // Ici, on pourrait vérifier la disponibilité du service externe
            return true; // Simplifié pour l'exemple
        }
        return true;
    }

    /**
     * Liste des canaux externes
     */
    public static List<CanalNotification> getCanauxExternes() {
        return Arrays.stream(values())
                .filter(CanalNotification::isExterne)
                .toList();
    }
}