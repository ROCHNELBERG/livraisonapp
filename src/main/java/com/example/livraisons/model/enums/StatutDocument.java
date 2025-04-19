package com.example.livraisons.model.enums;

public enum StatutDocument {
    EN_ATTENTE,
    REFUSE,
    VERIFIE,
    VALIDE // <- important car tu l'utilises dans `estValide()`
}
