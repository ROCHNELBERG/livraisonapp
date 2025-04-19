package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends Utilisateur {
    @Builder.Default
    @Column(name = "preference_contact")
    @Pattern(regexp = "^(EMAIL|SMS|APPEL)$", message = "préférence de contact invalide")
    private String preferenceContact = "EMAIL";

    @Builder.Default
    @Column(name = "notification_active")
    private boolean notificationActive = true;

    @Builder.Default
    @Column(name = "newsletter_abonne")
    private boolean newsletterAbonne = false;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "client_adresses_favorites", joinColumns = @JoinColumn(name = "client_id"))
    private List<Adresse> adressesFavorites = new ArrayList<>();

    @Past(message = "La date de naissance doit être dans le passé")
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "genre")
    @Pattern(regexp = "^(HOMME|FEMME|AUTRE)$", message = "genre invalide")
    private String genre;

    @Column(name = "photo_profil_url")
    private String photoProfilUrl;

    @Builder.Default
    @Column(name = "nombre_livraisons")
    @Min(value = 0, message = "Le nombre de livraisons ne peut pas être négatif")
    private int nombreLivraisons = 0;

    @Builder.Default
    @Column(name = "fidelite_points")
    @Min(value = 0, message = "Les points de fidélité ne peuvent pas être négatifs")
    private int fidelitePoints = 0;

    @Builder.Default
    @Column(name = "client_premium")
    private boolean clientPremium = false;

    @Column(name = "date_premium")
    private LocalDate datePremium;

    @Builder.Default
    @Column(name = "partage_doonnees")
    private boolean partageDonnees = false;

    @Column(name = "derniere_activite")
    private LocalDate derniereActivite;

    /**
     * Ajoute des points de fidélité au client
     * 
     * @param points Nombre de points à ajouter
     */
    public void ajouterPointsFidelite(int points) {
        if (points > 0) {
            this.fidelitePoints += points;
            if (this.fidelitePoints >= 100 && !this.clientPremium) {
                this.clientPremium = true;
                this.datePremium = LocalDate.now();
            }
        }
    }

    /**
     * Ajoute une adresse favorite
     * 
     * @param adresse Adresse à ajouter
     */
    public void ajouterAdresseFavorite(Adresse adresse) {
        if (adresse != null) {
            this.adressesFavorites.add(adresse);
        }
    }

}