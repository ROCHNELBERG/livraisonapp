package com.example.livraisons.model.enums;

/**
 * Options de livraison disponibles
 */
public enum TypeOptionLivraison {
    STANDARD, // Livraison standard (24-48h)
    EXPRESS, // Livraison express (moins de 24h)
    PROGRAMMEE, // Livraison à heure programmée
    GRUPPEE, // Livraison groupée
    NUIT // Livraison de nuit
}