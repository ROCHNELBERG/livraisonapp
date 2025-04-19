package com.example.livraisons.model.enums;

/**
 * Méthodes de paiement acceptées
 */
public enum MethodePaiement {
    CARTE_CREDIT(
            "Carte de crédit",
            true,
            "^[0-9]{16}$",
            "MM/YY",
            true),
    ORANGE_MONEY(
            "Orange Money",
            false,
            "^[0-9]{9}$",
            null,
            false),
    MTN_MONEY(
            "MTN Mobile Money",
            false,
            "^[0-9]{9}$",
            null,
            false),
    PAYPAL(
            "PayPal",
            true,
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            null,
            true),
    ESPECE(
            "Paiement en espèces",
            false,
            null,
            null,
            false);

    private final String libelle;
    private final boolean requiresCardInfo;
    private final String validationRegex;
    private final String dateFormat;
    private final boolean isOnline;

    MethodePaiement(String libelle, boolean requiresCardInfo,
            String validationRegex, String dateFormat,
            boolean isOnline) {
        this.libelle = libelle;
        this.requiresCardInfo = requiresCardInfo;
        this.validationRegex = validationRegex;
        this.dateFormat = dateFormat;
        this.isOnline = isOnline;
    }

    // Getters
    public String getLibelle() {
        return libelle;
    }

    public boolean requiresCardInfo() {
        return requiresCardInfo;
    }

    public String getValidationRegex() {
        return validationRegex;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public boolean isOnline() {
        return isOnline;
    }

    /**
     * Vérifie si une valeur est valide pour cette méthode de paiement
     */
    public boolean estValide(String valeur) {
        if (validationRegex == null)
            return true;
        return valeur != null && valeur.matches(validationRegex);
    }
}