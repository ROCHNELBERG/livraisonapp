package com.example.livraisons.model.enums;

/**
 * Canaux de diffusion des notifications
 */
public enum CanalNotification {
    EMAIL("Email", true),
    SMS("SMS", true),
    APP("Application", true),
    PUSH("Notification push", false),
    WHATSAPP("WhatsApp", false);

    private final String libelle;
    private final boolean actifParDefaut;

    CanalNotification(String libelle, boolean actifParDefaut) {
        this.libelle = libelle;
        this.actifParDefaut = actifParDefaut;
    }

    // Getters
    public String getLibelle() {
        return libelle;
    }

    public boolean isActifParDefaut() {
        return actifParDefaut;
    }
}