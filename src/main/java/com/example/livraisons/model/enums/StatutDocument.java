package com.example.livraisons.model.enums;

/**
 * Statuts des documents de vérification
 */
public enum StatutDocument {
    EN_ATTENTE, // En attente de vérification
    VERIFIE, // Document approuvé
    REJETE, // Document refusé
    EXPIRE // Document expiré
}