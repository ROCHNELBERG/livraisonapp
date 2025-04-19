package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import com.example.livraisons.model.enums.TypeConfirmation;

/**
 * Classe représentant un point de suivi d'une livraison
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "suivi_livraison")
public class Suivi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livraison_id", nullable = false)
    @NotNull(message = "La livraison est obligatoire")
    private Livraison livraison;

    @NotBlank(message = "Le statut est obligatoire")
    @Size(max = 100, message = "Le statut ne doit pas dépasser 100 caractères")
    private String statut;

    @Embedded
    @Valid
    private Position position;

    @NotNull(message = "La date est obligatoire")
    private LocalDateTime date;

    @Size(max = 500, message = "Le commentaire ne doit pas dépasser 500 caractères")
    private String commentaire;

    /**
     * Crée un nouveau point de suivi
     */
    public static Suivi creerPointSuivi(Livraison livraison, String statut, Position position, String commentaire) {
        return Suivi.builder()
                .livraison(livraison)
                .statut(statut)
                .position(position)
                .date(LocalDateTime.now())
                .commentaire(commentaire)
                .build();
    }

    /**
     * Vérifie si le point de suivi est valide
     */

    public boolean estValide() {
        return statut != null && !statut.isBlank() && position != null && position.estValide();
    }

    /**
     * Vérifie si le point de suivi est complet
     */

    public boolean estComplet() {
        return estValide() && date != null && commentaire != null && !commentaire.isBlank();
    }

    /**
     * Vérifie si le point de suivi est en cours
     */

    public boolean estEnCours() {
        return statut != null && statut.equalsIgnoreCase("EN_COURS");
    }

    /**
     * Vérifie si le point de suivi est terminé
     */

    public boolean estTermine() {
        return statut != null && statut.equalsIgnoreCase("TERMINE");

    }
}