package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;
import com.example.livraisons.model.enums.DispoonibiliteLivreur;

/**
 * Classe représentant un livreur de l'application.
 * Hérite de la classe Utilisateur.
 */

@Data // Lombok - Génère getters, setters, toString, equals, hashCode
@EqualsAndHashCode(callSuper = true) // Lombok - Génère equals et hashCode en tenant compte de la classe parente
@SuperBuilder // Lombok - Génère un constructeur avec tous les arguments de la classe parente
@NoArgsConstructor // Lombok - Génère un constructeur sans arguments
@AllArgsConstructor // Lombok - Génère un constructeur avec tous les arguments
@Entity
@Table(name = "livreurs")

public class Livreur extends Utilisateur {
    @Embedded // Indique que cette classe est une entité JPA et qu'elle utilise un type
              // embarqué
    @valid
    private vehicule vehicule;
    @Enumerated(EnumType.STRING) // Type de véhicule (voiture, moto, vélo)
    private DispoonibiliteLivreur disponibilite = DispoonibiliteLivreur.HORS_LiGNE; // Disponibilité du livreur

    @Embedded
    @valid
    private position position; // Position actuelle du livreur
    @Column(name = "Rayon_livraison")
    @Min(value = 0, message = "Le rayon de livraison ne peut pas être négatif")
    @DecimalMin(value = "1.0", message = "Le rayon de livraison doit être d'au moins 1 km")
    @DecimalMax(value = "50.0", message = "Le rayon de livraison ne peut pas dépasser 100 km")
    private double rayonLivraison;

    /**
     * 
     * Liste des documents de vérification du livreur.
     * Chaque document est une instance de la classe DocumentVerification.
     * La relation est de type un-à-plusieurs, ce qui signifie qu'un livreur peut
     * avoir plusieurs documents de vérification.
     * La cascade de type ALL signifie que toutes les opérations (ajout, mise à
     * jour, suppression) seront propagées aux documents de vérification.
     * L'option orphanRemoval=true signifie que si un document de vérification est
     * retiré de la liste, il sera également supprimé de la base de données.
     * La colonne de jointure dans la table Livraison est "livreur_id".
     * La liste est initialisée avec une nouvelle ArrayList pour éviter les
     * NullPointerException.
     * La validation est effectuée sur chaque document de vérification à l'aide de
     * l'annotation @Valid.
     * La classe DocumentVerification doit être annotée avec @Entity pour être
     * persistée dans la base de données.
     * La classe DocumentVerification doit également être définie avec ses propres
     * attributs et annotations JPA.
     * La classe DocumentVerification doit être importée pour être utilisée ici.
     * La classe DocumentVerification doit être définie dans le même package ou
     * importée correctement.
     * La classe DocumentVerification doit être annotée
     * avec @Data, @NoArgsConstructor, @AllArgsConstructor, etc. pour générer les
     * méthodes nécessaires.
     */

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // Relation un-à-plusieurs avec la classe Livraison
    @JoinColumn(name = "livreur_id") // Colonne de jointure dans la table Livraison
    private List<@Valid DocumentVerification> documentsVerifications = new ArrayList<>(); // Liste des documents de
                                                                                          // vérification du livreur

    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL, orphanRemoval = true) // Relation un-à-plusieurs avec la
                                                                                      // classe Livraison
    private List<Evaluation> evaluations = new ArrayList<>(); // Liste des évaluations du livreur

    @Embedded // Indique que cette classe est une entité JPA et qu'elle utilise un type
              // embarqué
    @Valid
    private StatistiquesLivreur statistiques; // Statistiques du livreur (nombre de livraisons, évaluations, etc.)

    /**
     * Vérifie si le livreur est disponible
     * 
     * @return true si disponible, false sinon
     * 
     */

    public boolean estDisponible() {
        return disponibilite == DispoonibiliteLivreur.DISPONIBLE
                && documentsVerifications.stream()
                        .allMatch(doc -> doc.getSatut() == StatutDocument.VERIFIE); // Vérifie si tous les documents
                                                                                    // sont vérifiés

    }

    /**
     * Ajoute un document de vérification à la liste des documents du livreur
     * 
     * @param document le document à ajouter
     */
    public void ajouterDocument(DocumentVerification document) {
        if (document != null) {
            documentsVerifications.add(document); // Ajoute le document à la liste
            document.setLivreur(this); // Définit le livreur du document
        }
    }

    /**
     * Vérifie si le livreur est valide pour la livraison
     * 
     * @return true si valide, false sinon
     */
    public boolean estValide() {
        return super.estValide() && vehicule != null && vehicule.estValide(); // Vérifie si le livreur et le véhicule
                                                                              // sont valides

    }

    /**
     * Vérifie si le livreur est en ligne
     * 
     * @return true si en ligne, false sinon
     */

    public boolean estEnLigne() {
        return disponibilite == DispoonibiliteLivreur.DISPONIBLE; // Vérifie si le livreur est en ligne
    }

    /**
     * Vérifie si le livreur est hors ligne
     * 
     * @return true si hors ligne, false sinon
     */

    public boolean estHorsLigne() {
        return disponibilite == DispoonibiliteLivreur.HORS_LiGNE;

    } // Vérifie si le livreur est hors ligne

    /**
     * Vérifie si le livreur est en pause
     * 
     * @return true si en pause, false sinon
     */
    public boolean estEnPause() {
        return disponibilite == DispoonibiliteLivreur.EN_PAUSE; // Vérifie si le livreur est en pause

    } // Vérifie si le livreur est en pause

    /**
     * Vérifie si le livreur est en attente de validation
     * 
     * @return true si en attente, false sinon
     */

    public boolean estEnAttente() {
        return disponibilite == DispoonibiliteLivreur.EN_ATTENTE; // Vérifie si le livreur est en attente de validation
    }

    /**
     * Vérifie si le livreur est en ligne
     * 
     * @return true si en ligne, false sinon
     */
    public boolean estEnLigne() {
        return disponibilite == DispoonibiliteLivreur.DISPONIBLE; // Vérifie si le livreur est en ligne
    }

}