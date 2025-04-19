package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import com.example.livraisons.model.enums.StatutUtilisateur;
import com.example.livraisons.model.enums.MethodeAuthentification;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Classe représentant un utilisateur dans le système de livraison.
 * Implémente UserDetails pour l'intégration avec Spring Security.
 */
@Data // Lombok - Génère getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok - Génère un constructeur sans arguments
@AllArgsConstructor // Lombok - Génère un constructeur avec tous les arguments
@Inheritance(strategy = InheritanceType.JOINED) // Stratégie d'héritage pour JPA (une table par classe)
@Entity // Indique que cette classe est une entité JPA
@Table(name = "utilisateur") // Spécifie le nom de la table dans la base de données
public class Utilisateur implements UserDetails {

    // Identifiant unique auto-généré
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom de l'utilisateur avec validation
    @NotBlank(message = "Le nom d'utilisateur est requis")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères")
    private String nom;

    // Prénom de l'utilisateur avec validation
    @NotBlank(message = "le prénom est requis")
    @Size(min = 3, max = 50, message = "le prénom doit contenir entre 3 et 50 caractères")
    private String prenom;

    // Email avec validation et contrainte d'unicité
    @NotBlank(message = "L'email est requis")
    @Email(message = "L'email doit être valide")
    @Size(max = 100, message = "L'email ne doit pas dépasser 100 caractères")
    @Column(unique = true)
    private String email;

    // Numéro de téléphone avec validation complexe
    @NotBlank(message = "le numéro de téléphone est requis")
    @Size(min = 10, max = 15, message = "le numéro de téléphone doit contenir entre 10 et 15 caractères")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "le numéro de téléphone doit être valide")
    private String telephone;

    // Mot de passe avec validation
    @NotBlank(message = "le mot de passe est requis")
    @Size(min = 5, message = "le mot de passe doit contenir au moins 8 caractères")
    private String motDePasse;

    // Adresse embarquée (stockée dans la même table)
    @Embedded
    private Adresse adresse;

    // Liste des méthodes d'authentification (stockée dans une table séparée)
    @ElementCollection
    @CollectionTable(name = "utilisateur_methodes_auth", joinColumns = @JoinColumn(name = "utilisateur_id"))
    @Column(name = "methode")
    private List<MethodeAuthentification> methodesAuthentification;

    // Date d'inscription auto-générée
    @CreationTimestamp
    private LocalDateTime dateInscription;

    // Date de dernière connexion (à mettre à jour manuellement)
    private LocalDateTime derniereConnexion;

    // Note moyenne avec validation
    @DecimalMin(value = "0.0", message = "la note moyenne doit être au moins 0.0")
    @DecimalMax(value = "5.0", message = "la note moyenne doit être au plus 5.0")
    private Double evaluationMoyenne;

    // Statut de l'utilisateur (enum stocké sous forme de string)
    @Enumerated(EnumType.STRING)
    private StatutUtilisateur statut = StatutUtilisateur.ACTIF;

    /* ===================== */
    /* Méthodes Spring Security */
    /* ===================== */

    /**
     * Retourne les autorisations/rôles de l'utilisateur.
     * Ici, on utilise le nom simple de la classe comme autorité.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getClass().getSimpleName()));
    }

    /**
     * Retourne le mot de passe pour l'authentification.
     */
    @Override
    public String getPassword() {
        return motDePasse;
    }

    /**
     * Retourne l'email comme identifiant (username).
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Vérifie si le compte n'est pas expiré.
     * Ici, les comptes n'expirent jamais.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Vérifie si le compte n'est pas verrouillé.
     * Le compte est verrouillé si le statut est SUSPENDU.
     */
    @Override
    public boolean isAccountNonLocked() {
        return statut != StatutUtilisateur.SUSPENDU;
    }

    /**
     * Vérifie si les credentials ne sont pas expirés.
     * Ici, les credentials n'expirent jamais.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Vérifie si le compte est activé.
     * Le compte est activé seulement si le statut est ACTIF.
     */
    @Override
    public boolean isEnabled() {
        return statut == StatutUtilisateur.ACTIF;
    }
}