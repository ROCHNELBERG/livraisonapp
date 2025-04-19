package com.example.livraisons.model;

import jakarta.persistence.*;// import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*; // import jakarta.validation.constraints.Max;
import lombok.*;
import java.time.LocalDateTime;
import com.example.livraisons.model.enums.TypeConfirmation;

/**
 * Classe représentant une évaluation
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluation {

    @Min(value = 1, message = "La note doit être au moins 1")
    @Max(value = 5, message = "La note doit être au plus 5")
    private Integer note;

    @Size(max = 500, message = "Le commentaire ne doit pas dépasser 500 caractères")
    private String commentaire;

    private LocalDateTime dateEvaluation; // Date de l'évaluation

    /**
     * Vérifie si l'évaluation est complète
     */
    public boolean estComplete() {
        return note != null && note >= 1 && note <= 5;
    }

}