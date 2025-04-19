package com.example.livraisons.model.enums;

/**
 * Enumération des statuts possibles d'une livraison
 */
public enum StatutLivraison {
   EN_ATTENTE, // Livraison en attente d'acceptation
   EN_COURS, // Livraison en cours de traitement
   ACCEPTER, // Livraison acceptée par le livreur
   RECUPERE, // Livraison récupérée par le livreur
   LIVREE, // Livraison livrée au client
   ANNULEE, // Livraison annulée
   RETARDEE, // Livraison retardée

}
