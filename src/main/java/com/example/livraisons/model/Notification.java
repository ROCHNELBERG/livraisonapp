package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import com.example.livraisons.model.enums.CanalNotification;
import com.example.livraisons.model.enums.TypeNotification;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


/**
 * Représente une notification envoyée à un utilisateur
 * Peut être liée à une livraison spécifique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relation avec l'utilisateur destinataire
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @NotNull(message = "L'utilisateur destinataire est obligatoire")
    private Utilisateur utilisateur;

    // Type de notification (enum)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le type de notification est obligatoire")
    private TypeNotification type;

    // Titre et contenu de la notification
    @NotBlank(message = "Le titre de la notification est obligatoire")
    @Size(max = 100, message = "Le titre ne doit pas dépasser 100 caractères")
    private String titre;

    @NotBlank(message = "Le contenu de la notification est obligatoire")
    @Size(max = 1000, message = "Le contenu ne doit pas dépasser 1000 caractères")
    private String contenu;

    // Relation optionnelle avec une livraison
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livraison_id")
    private Livraison livraison;

    // Statut de lecture
    @Builder.Default
    private boolean lu = false;

    // Date d'envoi
    @NotNull(message = "La date d'envoi est obligatoire")
    private LocalDateTime date;

    // Canal d'envoi (enum)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le canal de notification est obligatoire")
    private CanalNotification canal;

    /* Méthodes utilitaires */

    /**
     * Marque la notification comme lue
     * 
     * @return true si la notification était non lue, false sinon
     */
    public boolean marquerCommeLue() {
        if (!this.lu) {
            this.lu = true;
            return true;
        }
        return false;
    }

    /**
     * Vérifie si la notification est liée à une livraison
     * 
     * @return true si liée à une livraison, false sinon
     */
    public boolean estLieeALivraison() {
        return this.livraison != null;
    }

    /**
     * Vérifie si la notification est prête à être envoyée
     * 
     * @return true si valide, false sinon
     */
    public boolean estValidePourEnvoi() {
        return this.utilisateur != null
                && this.type != null
                && this.titre != null && !this.titre.isBlank()
                && this.contenu != null && !this.contenu.isBlank()
                && this.date != null
                && this.canal != null;
    }

    /**
     * Crée un résumé de la notification pour les logs
     * 
     * @return String formatée contenant les infos principales
     */
    public String toLogString() {
        return String.format(
                "Notification [ID: %d, Type: %s, Pour: %s, Lu: %s]",
                this.id,
                this.type != null ? this.type.name() : "null",
                this.utilisateur != null ? this.utilisateur.getEmail() : "null",
                this.lu ? "Oui" : "Non");
    }
}