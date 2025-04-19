package com.example.livraisons.model.enums;

/**
 * Méthodes de confirmation de livraison
 */
public enum TypeConfirmation {
    SIGNATURE("Signature du destinataire"),
    QRCODE("Scan du QR code"),
    PHOTO("Photo de preuve"),
    CODE("Code à usage unique"),
    APPEL("Appel téléphonique"),
    AUCUNE("Aucune confirmation requise");

    private final String description;

    TypeConfirmation(String description) {
        this.description = description;
    }

    // Getter
    public String getDescription() {
        return description;
    }

    /**
     * Vérifie si une preuve est requise
     */
    public boolean necessitePreuve() {
        return this != AUCUNE;
    }
}