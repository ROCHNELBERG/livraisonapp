package com.example.livraisons.model.enums;

/**
 * Statuts de disponibilité des livreurs
 */
public enum DisponibiliteLivreur {
    DISPONIBLE, // Disponible pour des livraisons
    OCCUPE, // Actuellement en livraison
    EN_PAUSE, // En pause courte
    HORS_LIGNE // Non disponible
}