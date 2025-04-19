package com.example.livraisons.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.livraisons.model.enums.MethodeAuthentification;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Classe représentant les informations d'authentification d'un utilisateur
 * Implémente UserDetails pour l'intégration avec Spring Security
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authentifications", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "telephone")
})
public class Authentification implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @NotNull(message = "L'utilisateur associé est obligatoire")
    private Utilisateur utilisateur;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Numéro de téléphone invalide")
    @Column(nullable = false)
    private String telephone;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Column(nullable = false)
    private String motDePasse;

    @ElementCollection(targetClass = MethodeAuthentification.class)
    @CollectionTable(name = "authentification_methodes", joinColumns = @JoinColumn(name = "authentification_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "methode")
    @NotEmpty(message = "Au moins une méthode d'authentification est requise")
    @Builder.Default
    private List<MethodeAuthentification> methodesAuthentification = List.of();

    @Column(name = "is_email_verified", nullable = false)
    @Builder.Default
    private boolean emailVerified = false;

    @Column(name = "is_phone_verified", nullable = false)
    @Builder.Default
    private boolean phoneVerified = false;
    @Builder.Default
    @Column(name = "is_active", nullable = false)

    private boolean active = true;

    @Column(name = "is_locked", nullable = false)
    @Builder.Default
    private boolean locked = false;

    @Column(name = "is_expired", nullable = false)
    @Builder.Default
    private boolean expired = false;

    @Column(name = "credentials_expired", nullable = false)
    @Builder.Default
    private boolean credentialsExpired = false;

    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;

    @UpdateTimestamp
    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expiry")
    private LocalDateTime refreshTokenExpiry;

    // Méthodes de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(utilisateur.getClass().getSimpleName()));
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    // Méthodes utilitaires
    public void markAsLoggedIn() {
        this.derniereConnexion = LocalDateTime.now();
    }

    public void updateRefreshToken(String refreshToken, LocalDateTime expiryDate) {
        this.refreshToken = refreshToken;
        this.refreshTokenExpiry = expiryDate;
    }

    public boolean isRefreshTokenValid() {
        return refreshToken != null &&
                refreshTokenExpiry != null &&
                refreshTokenExpiry.isAfter(LocalDateTime.now());
    }

    public boolean isSocialLogin() {
        return methodesAuthentification.stream()
                .anyMatch(m -> m != MethodeAuthentification.EMAIL);
    }

    /**
     * Vérifie si l'authentification est complète (email et téléphone vérifiés)
     */
    public boolean isComplete() {
        return emailVerified && phoneVerified;
    }

    /**
     * Vérifie si l'authentification est valide (non expirée et non verrouillée)
     */
    public boolean isValid() {
        return !expired && !locked && isComplete();
    }

    /**
     * Vérifie si l'authentification est active
     */

    public boolean isActive() {
        return active;
    }

    /**
     * Vérifie si l'authentification est verrouillée
     */

    public boolean isLocked() {
        return locked;

    }

    /**
     * Vérifie si l'authentification est expirée
     */

    public boolean isExpired() {
        return expired;
    }

    /**
     * Vérifie si les informations d'identification sont expirées
     */

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    /**
     * Vérifie si l'authentification est vérifiée par email
     */

    public boolean isEmailVerified() {
        return emailVerified;

    }

    /**
     * Vérifie si l'authentification est vérifiée par téléphone
     */
    public boolean isPhoneVerified() {
        return phoneVerified;

    }

}