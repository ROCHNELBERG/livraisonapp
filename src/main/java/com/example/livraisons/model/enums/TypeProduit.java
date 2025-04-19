package com.example.livraisons.model.enums;

/**
 * Tailles de produits pour la livraison
 */
public enum TypeProduit {
    PETIT, // < 1kg, petit volume
    MOYEN, // 1-5kg, volume moyen
    GRAND, // 5-20kg, gros volume
    TRES_GRAND, // >20kg, volume très important
    FRAGILE // Article fragile (gestion spéciale)
}