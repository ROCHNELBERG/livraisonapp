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
    ECHOUE("Échec de la livraison"),
    RETOUR("Retour de la livraison"),
    LIVRAISON("Livraison réussie"),
    SMS("SMS de confirmation"),
    EMAIL("Email de confirmation"),
    EN_COURS("En cours de livraison"),

    AUCUNE("Aucune preuve requise");

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

    /**
     * Vérifie si la méthode de confirmation est valide
     */
    public boolean estValide() {
        return this != AUCUNE && this != ECHOUE && this != RETOUR;
    }

    /**
     * Vérifie si la méthode de confirmation est valide pour une livraison réussie
     */

    public boolean estValidePourLivraison() {
        return this == LIVRAISON || this == SIGNATURE || this == QRCODE || this == PHOTO || this == CODE;
    }

    /**
     * Vérifie si la méthode de confirmation est valide pour une livraison échouée
     */

    public boolean estValidePourEchec() {
        return this == ECHOUE || this == RETOUR;
    }

    /**
     * Vérifie si la méthode de confirmation est valide pour une livraison annulée
     */

    public boolean estValidePourAnnulation() {
        return this == ECHOUE || this == RETOUR;

    }

    /**
     * Vérifie si la méthode de confirmation est valide pour une livraison en cours
     */

    public boolean estValidePourEnCours() {
        return this == EN_COURS || this == RETOUR || this == ECHOUE;
    }

    /**
     * Vérifie si la méthode de confirmation est valide pour une livraison en
     * attente
     */

    public boolean estValidePourEnAttente() {
        return this == EN_COURS || this == RETOUR || this == ECHOUE;
    }

    /**
     * Vérifie si la méthode de confirmation est valide pour une livraison en
     * attente de vérification
     */

    public boolean estValidePourEnAttenteVerification() {
        return this == EN_COURS || this == RETOUR || this == ECHOUE;
    }

    /**
     * Vérifie si la méthode de confirmation est valide pour une livraison en
     * attente de validation
     */

    public boolean estValidePourEnAttenteValidation() {
        return this == EN_COURS || this == RETOUR || this == ECHOUE;
    }
}