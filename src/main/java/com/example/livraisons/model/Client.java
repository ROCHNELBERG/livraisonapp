package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

/**
 * Classe représentant un client de l'application.
 * Hérite de la classe Utilisateur.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")

public class Client extends Utilisateur {
    @Column(name = "preference_contact")
    @Pattern(regexp = "^(EMAIL|SMS|APPEL)$", message = "préférence de contact invalide")

    private String preferenceContact = "EMAIL";

    @Column(name = "notification_active")
    private boolean notificationActive = true;
    @Column(name = "newsletter_abonne")
    private boolean newsletterAbonne = false;
    @ElementCollection
    @CollectionTable(name = "client_adresses_favorites", joinColumns = @JoinColumn(name = "client_id"))
    private List<@valid Adresse> adressesFavorites = new ArrayList<>();
    @Past(message = "La date de naissance doit être dans le passé")
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    @Column(name = "genre")
    @Pattern(regexp = "^(HOMME|FEMME|AUTRE)$", message = "genre invalide")
    private String genre;
    @Column(name = "photo_profil_url")
    private String photoProfilUrl;
    @Column(name = "nombre_livraisons")
    @Min(value = 0, message = "Le nombre de livraisons ne peut pas être négatif")
    private int nombreLivraisons = 0;
    @Column(name = "fidelite_points")
    @Min(value = 0, message = "Les points de fidélité ne peuvent pas être négatifs")
    private int fidelitePoints = 0;
    @Column(name = "client_premium")
    private boolean clientPremium = false;
    @Column(name = "date_premium")
    private LocalDate datePremium;
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