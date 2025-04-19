package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import com.example.livraisons.model.enums.StatutDocument;
import com.example.livraisons.model.enums.TypeDocument;

/**
 * Classe représentant un document de vérification pour les livreurs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "documents_verification")
public class DocumentVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant unique du document

    @Enumerated(EnumType.STRING) // Type de l'énumération pour la base de données
    @NotNull(message = "le type de document est obligatoire")
    private TypeDocument type; // Type de document (CNI, permis de conduire, etc.)

    @NotBlank(message = "l'URL du document est obligatoire")
    @Size(min = 3, max = 255, message = "l'URL du document doit contenir entre 3 et 255 caractères")
    private String url; // URL du document

    @NotNull(message = "la date de verification est obligatoire")
    private LocalDate dateVerification; // Date de vérification du document

    @Enumerated(EnumType.STRING) // Type de l'énumération pour la base de données
    @NotNull(message = "le statut  est obligatoire")
    private StatutDocument statut = StatutDocument.EN_ATTENTE; // Statut du document (EN_ATTENTE, VALIDE, REFUSE)

    @Size(max = 255, message = "les commentaires ne doivent pas dépasser 255 caractères")
    private String commentaire; // Commentaires sur le document

    /**
     * Vérifie si le document est vérifié
     */
    public boolean estVerifie() {
        return statut == StatutDocument.VERIFIE;
    }

    /**
     * Vérifie si le document est valide
     */
    public boolean estValide() {
        return statut == StatutDocument.VALIDE;

    }

    /**
     * Vérifie si le document est refusé
     */

    public boolean estRefuse() {
        return statut == StatutDocument.REFUSE;
    }

    /**
     * Vérifie si le document est en attente de vérification
     */

    public boolean estEnAttente() {
        return statut == StatutDocument.EN_ATTENTE;
    }

    /**
     * Vérifie si le document est expiré
     */

    public boolean estExpire() {
        return LocalDate.now().isAfter(dateVerification.plusYears(1)); // Exemple : document valide pendant 1 an

    }

    /**
     * Vérifie si le document est valide pour la livraison
     */

    public boolean estValidePourLivraison() {
        return estValide() && !estExpire(); // Le document doit être valide et non expiré
    }

}