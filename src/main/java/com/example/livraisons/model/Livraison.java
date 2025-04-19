package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.livraisons.model.enums.StatutLivraison;
import com.example.livraisons.model.enums.TypeOptionLivraison;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une livraison dans le système de livraison.
 */
@Data // Lombok - Génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok - Génère un constructeur sans arguments
@AllArgsConstructor // Lombok - Génère un constructeur avec tous les arguments
@Builder // Lombok - Génère un constructeur avec tous les arguments de la classe
@Entity // Indique que cette classe est une entité JPA
@Table(name = "livraisons") // Spécifie le nom de la table dans la base de données

public class Livraison {

    @Id // Indique que cet attribut est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'identifiant
    private Long id; // Identifiant unique de la livraison

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "le client est obligatoire")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livreur_id")
    private Livreur livreur; // Livreur associé à la livraison

    @Enumerated(EnumType.STRING) // Type de l'énumération pour la base de données
    @NotNull(message = "le statut de la livraison est obligatoire")
    private StatutLivraison statut = StatutLivraison.EN_ATTENTE; // Statut de la livraison (EN_ATTENTE, EN_COURS,
                                                                 // LIVREE, ANNULEE)

    @Embedded // Indique que cette classe est une entité JPA et qu'elle utilise un type
              // embarqué
    @AttributeOverrides({
            @AttributeOverride(name = "nom", column = @Column(name = "recup_nom")), // Nom de l'adresse de récupération
            @AttributeOverride(name = "rue", column = @Column(name = "recup_rue")),
            @AttributeOverride(name = "ville", column = @Column(name = "recup_ville")),
            @AttributeOverride(name = "codePostal", column = @Column(name = "recup_code_postal")),
            @AttributeOverride(name = "pays", column = @Column(name = "recup_pays")),
            @AttributeOverride(name = "instructions", column = @Column(name = "recup_instructions"))
    })

    @Valid
    @NotNull(message = "l'adresse de recuperation est obligatoire")
    private Adresse adresseRecuperation; // Adresse de récupération de la livraison

    @Embedded // Indique que cette classe est une entité JPA et qu'elle utilise un type
              // embarqué
    @AttributeOverrides({
            @AttributeOverride(name = "nom", column = @Column(name = "livraison_nom")), // Nom de l'adresse de livraison
            @AttributeOverride(name = "rue", column = @Column(name = "livraison_rue")),
            @AttributeOverride(name = "ville", column = @Column(name = "livraison_ville")),
            @AttributeOverride(name = "codePostal", column = @Column(name = "livraison_code_postal")),
            @AttributeOverride(name = "pays", column = @Column(name = "livraison_pays")),
            @AttributeOverride(name = "instructions", column = @Column(name = "livraison_instructions"))

    })
    @Valid
    @NotNull(message = "l'adresse de livraison est obligatoire")
    private Adresse adresseLivraison; // Adresse de livraison de la livraison

    @Embedded // Indique que cette classe est une entité JPA et qu'elle utilise un type
              // embarqué
    @Valid
    @NotNull(message = "les informations sur le produit sont obligatoires")
    private Produit produit; // Informations sur le produit à livrer

    @Enumerated(EnumType.STRING) // Type de l'énumération pour la base de données
    @NotNull(message = "l'option de livraison est obligatoire")
    @Builder.Default // Définit une valeur par défaut pour le champ
    private TypeOptionLivraison typeOptionLivraison = TypeOptionLivraison.STANDARD; // Type d'option de livraison
                                                                                    // (STANDARD, EXPRESS, PRIORITAIRE)

    @Future(message = "L'heure programmée doit être dans le futur")
    private LocalDateTime heureProgrammee; // Heure de livraison programmée

    @Embedded
    @Valid
    private Assurance assurance; // Assurance de la livraison

    @Embedded // Indique que cette classe est une entité JPA et qu'elle utilise un type
              // embarqué
    @Valid
    @NotNull(message = "les informations de paiement sont obligatoires")
    private Paiement paiement; // Informations de paiement de la livraison

    @Embedded // Indique que cette classe est une entité JPA et qu'elle utilise un type
              // embarqué
    private Confirmation confirmation; // Confirmation de la livraison

    @OneToMany(mappedBy = "livraison", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Suivi> suivi = new ArrayList<>();

    @CreationTimestamp // Timestamp de création automatique
    private LocalDateTime dateCreation; // Date de création de la livraison

    @UpdateTimestamp // Timestamp de mise à jour automatique
    private LocalDateTime dateModification; // Date de dernière modification de la livraison

    /**
     * Ajoute un élément de suivi à la livraison
     * 
     * @param suivi Elément de suivi à ajouter
     */

    public void ajouterSuivi(Suivi suivi) {
        if (suivi != null) {
            suivi.setLivraison(this);
            this.suivi.add(suivi);
        }
    }

    /**
     * Vérifie si la livraison peut être acceptée par un livreur
     * 
     * @return true si acceptation possible, false sinon
     */

    public boolean peutEtreAcceptee() {
        return statut == StatutLivraison.EN_ATTENTE;
    }

    /**
     * Vérifie si la livraison est valide
     * 
     * @return true si valide, false sinon
     */

    public boolean adressesSontValides() {
        return adresseRecuperation != null && adresseRecuperation.estValide() &&
                adresseLivraison != null && adresseLivraison.estValide();
    }

    /**
     * Vérifie si la livraison est en cours
     * 
     * @return true si en cours, false sinon
     */

    public boolean estEnCours() {
        return statut == StatutLivraison.EN_COURS; // Vérifie si le statut de la livraison est EN_COURS
    }

    /**
     * Vérifie si la livraison est livrée
     * 
     * @return true si livrée, false sinon
     */

    public boolean estLivree() {
        return statut == StatutLivraison.LIVREE; // Vérifie si le statut de la livraison est LIVREE
    }

    /**
     * Vérifie si la livraison est annulée
     * 
     * @return true si annulée, false sinon
     */

    public boolean estAnnulee() {
        return statut == StatutLivraison.ANNULEE; // Vérifie si le statut de la livraison est ANNULEE
    }

}
