package com.example.livraisons.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import com.example.livraisons.model.enums.TypeConfirmation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Classe embarquée pour la confirmation de livraison
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Confirmation {

    @Enumerated(EnumType.STRING)
    private TypeConfirmation type;

    @Size(max = 255, message = "L'URL de la preuve ne doit pas dépasser 255 caractères")
    private String preuveUrl;

    private LocalDateTime date;

    @Size(max = 500, message = "Les notes ne doivent pas dépasser 500 caractères")
    private String notes;

    /**
     * Vérifie si la livraison est confirmée
     */
    public boolean estConfirmee() {
        return type != null && preuveUrl != null && !preuveUrl.isBlank();
    }

    /**
     * Vérifie si la livraison est en attente de confirmation
     */

    public boolean estEnAttente() {
        return type == null && (preuveUrl == null || preuveUrl.isBlank());
    }

    /**
     * Vérifie si la livraison est échouée
     */

    public boolean estEchouee() {
        return type == TypeConfirmation.ECHOUE;

    }

    /**
     * Vérifie si la livraison est valide
     */
    public boolean estValide() {
        return type != null && preuveUrl != null && !preuveUrl.isBlank() && date != null;

    }
}