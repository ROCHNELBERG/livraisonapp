package com.example.livraisons.model;

import com.example.livraisons.model.enums.MethodePaiement;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoyenPaiement {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le type de paiement est obligatoire")
    private MethodePaiement type;

    @Size(min = 16, max = 19, message = "Le numéro de carte doit contenir entre 16 et 19 chiffres")
    @Column(name = "numero_carte")
    private String numeroCarte; // Pour les paiements par carte

    @Size(max = 100, message = "Le nom du titulaire ne doit pas dépasser 100 caractères")
    @Column(name = "nom_titulaire")
    private String nomTitulaire; // Pour les cartes

    @Size(min = 4, max = 5, message = "Format de date invalide (MM/YY)")
    @Column(name = "date_expiration")
    private String dateExpiration; // Pour les cartes (MM/YY)

    @Size(min = 9, max = 12, message = "Le numéro mobile doit contenir entre 9 et 12 chiffres")
    @Column(name = "numero_mobile")
    private String numeroMobile; // Pour mobile money

    @Column(name = "est_principal")
    @Builder.Default
    private boolean estPrincipal = false;

    /**
     * Vérifie si le moyen de paiement est valide
     */
    public boolean estValide() {
        if (type == null)
            return false;

        // Validation selon le type de paiement
        switch (type) {
            case CARTE_CREDIT:
                return numeroCarte != null && numeroCarte.matches("^[0-9]{16}$")
                        && dateExpiration != null && dateExpiration.matches("^(0[1-9]|1[0-2])/[0-9]{2}$")
                        && nomTitulaire != null && !nomTitulaire.isBlank();

            case ORANGE_MONEY:
            case MTN_MONEY:
                return numeroMobile != null && numeroMobile.matches("^[0-9]{9,12}$");

            case PAYPAL:
                return numeroCarte != null && numeroCarte.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

            case ESPECE:
                return true;

            default:
                return false;
        }
    }

    /**
     * Formate le numéro de carte/mobile pour l'affichage
     */
    public String getNumeroMasque() {
        if (type == MethodePaiement.CARTE_CREDIT && numeroCarte != null && numeroCarte.length() > 4) {
            return "**** **** **** " + numeroCarte.substring(numeroCarte.length() - 4);
        }
        if ((type == MethodePaiement.ORANGE_MONEY || type == MethodePaiement.MTN_MONEY)
                && numeroMobile != null && numeroMobile.length() > 3) {
            return "*** *** " + numeroMobile.substring(numeroMobile.length() - 3);
        }
        return "****";
    }

    /**
     * Vérifie si le moyen de paiement est principal
     */
    public boolean estPrincipal() {
        return estPrincipal;

    }

    /**
     * Vérifie si le moyen de paiement est valide pour le paiement
     */

    public boolean estValidePourPaiement() {
        return estValide() && (type == MethodePaiement.CARTE_CREDIT || type == MethodePaiement.ORANGE_MONEY
                || type == MethodePaiement.MTN_MONEY);
    }

    /**
     * Vérifie si le moyen de paiement est valide pour le remboursement
     */

    public boolean estValidePourRemboursement() {
        return estValide() && (type == MethodePaiement.CARTE_CREDIT || type == MethodePaiement.PAYPAL);
    }

    /**
     * Vérifie si le moyen de paiement est valide pour la confirmation
     */

}