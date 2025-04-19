package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livraison_id", nullable = false)
    @NotNull(message = "La livraison est obligatoire")
    private Livraison livraison;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expediteur_id", nullable = false)
    @NotNull(message = "L'expéditeur est obligatoire")
    private Utilisateur expediteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destinataire_id", nullable = false)
    @NotNull(message = "Le destinataire est obligatoire")
    private Utilisateur destinataire;

    @NotBlank(message = "Le contenu est obligatoire")
    @Size(max = 2000, message = "Le contenu ne doit pas dépasser 2000 caractères")
    private String contenu;
    @Builder.Default
    private boolean lu = false;

    @NotNull(message = "La date est obligatoire")
    private LocalDateTime date;

    /**
     * Marque le message comme lu
     */
    public void marquerCommeLu() {
        this.lu = true;

    }

    /**
     * Vérifie si le message est valide
     */
    public boolean estValide() {
        return contenu != null && !contenu.isBlank() && expediteur != null && destinataire != null;
    }

    /**
     * Vérifie si le message est lu
     */

    public boolean estLu() {
        return lu;
    }

    /**
     * Vérifie si le message est non lu
     */

    public boolean estNonLu() {
        return !lu;
    }

    /**
     * Vérifie si le message est récent (moins de 24 heures)
     */

    public boolean estRecent() {
        return date != null && date.isAfter(LocalDateTime.now().minusHours(24));
    }

    /**
     * Vérifie si le message est ancien (plus de 24 heures)
     */

    public boolean estAncien() {
        return date != null && date.isBefore(LocalDateTime.now().minusHours(24));
    }

    /**
     * Vérifie si le message est envoyé par l'expéditeur
     */

    public boolean estEnvoyeParExpediteur(Utilisateur utilisateur) {
        return expediteur != null && expediteur.equals(utilisateur);

    }

    /**
     * Vérifie si le message est envoyé au destinataire
     */

    public boolean estEnvoyeAuDestinataire(Utilisateur utilisateur) {
        return destinataire != null && destinataire.equals(utilisateur);

    }

    /**
     * Vérifie si le message est envoyé à un utilisateur spécifique
     */

    public boolean estEnvoyeA(Utilisateur utilisateur) {
        return expediteur != null && expediteur.equals(utilisateur)
                || destinataire != null && destinataire.equals(utilisateur);

    }

}