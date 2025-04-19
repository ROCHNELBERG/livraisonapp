package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

import com.example.livraisons.model.enums.StatutDocument;
import com.example.livraisons.model.enums.TypeDocument;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "documents_verification")
public class DocumentVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le type de document est obligatoire")
    private TypeDocument type;

    @NotBlank(message = "L'URL du document est obligatoire")
    @Size(min = 3, max = 255, message = "L'URL du document doit contenir entre 3 et 255 caractères")
    private String url;

    @NotNull(message = "La date de vérification est obligatoire")
    private LocalDate dateVerification;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le statut est obligatoire")
    @Builder.Default
    private StatutDocument statut = StatutDocument.EN_ATTENTE;

    @Size(max = 255, message = "Les commentaires ne doivent pas dépasser 255 caractères")
    private String commentaire;

    public boolean estVerifie() {
        return statut == StatutDocument.VERIFIE;
    }

    public boolean estValide() {
        return statut == StatutDocument.VALIDE;
    }

    public boolean estRefuse() {
        return statut == StatutDocument.REFUSE;
    }

    public boolean estEnAttente() {
        return statut == StatutDocument.EN_ATTENTE;
    }

    public boolean estExpire() {
        return dateVerification != null && LocalDate.now().isAfter(dateVerification.plusYears(1));
    }
}
