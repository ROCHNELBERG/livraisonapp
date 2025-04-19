package com.example.livraisons.model.enums;

/**
 * Statuts possibles d'un paiement
 */
public enum StatutPaiement {
    EN_ATTENTE, // Paiement initié mais non confirmé
    PAYE, // Paiement confirmé
    ECHEC, // Paiement échoué
    REMBOURSE, // Paiement remboursé
    EN_CONTROLE // Paiement en cours de vérification
}