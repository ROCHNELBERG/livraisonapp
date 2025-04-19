package com.example.livraisons.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import com.example.livraisons.model.enums.MethodePaiement;
import com.example.livraisons.model.enums.StatutPaiement;

/**
 * Classe embarquée pour les informations de paiement
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "La méthode de paiement est obligatoire")
    private MethodePaiement methode;

    @Positive(message = "Le montant doit être positif")
    @DecimalMax(value = "500000.0", message = "Le montant ne peut excéder 500 000")
    private Double montant;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut est obligatoire")
    private StatutPaiement statut = StatutPaiement.EN_ATTENTE;

    @Size(max = 100, message = "L'ID de transaction ne doit pas dépasser 100 caractères")
    private String transactionId;

    private LocalDateTime date;

    /**
     * Vérifie si le paiement est complet
     */
    public boolean estPaye() {
        return statut == StatutPaiement.PAYE;
    }

    /**
     * Vérifie si le paiement est en attente
     */

    public boolean estEnAttente() {
        return statut == StatutPaiement.EN_ATTENTE;
    }

    /**
     * Vérifie si le paiement est échoué
     */

    public boolean estEchoue() {
        return statut == StatutPaiement.ECHEC;

    }

    /**
     * Vérifie si le paiement est valide
     */

    public boolean estValide() {
        return montant != null && montant > 0 && methode != null && statut != null;
    }

}