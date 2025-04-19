package com.example.livraisons.model.enums;

/**
 * Classification des produits pour la logistique
 */
public enum TypeProduit {
    PETIT( // Colis < 1kg, dimensions < 25cm
            0.5, // Tarif de base
            1.0, // Poids max kg
            25.0 // Dimension max cm
    ),
    MOYEN( // Colis 1-5kg, dimensions < 50cm
            1.0,
            5.0,
            50.0),
    GRAND( // Colis 5-20kg, dimensions < 120cm
            1.8,
            20.0,
            120.0),
    TRES_GRAND( // Colis >20kg, dimensions >120cm
            3.0,
            100.0,
            300.0),
    FRAGILE( // Articles fragiles (surcharge)
            2.0,
            10.0,
            60.0);

    private final double tarifBase;
    private final double poidsMax;
    private final double dimensionMax;

    TypeProduit(double tarifBase, double poidsMax, double dimensionMax) {
        this.tarifBase = tarifBase;
        this.poidsMax = poidsMax;
        this.dimensionMax = dimensionMax;
    }

    // Getters
    public double getTarifBase() {
        return tarifBase;
    }

    public double getPoidsMax() {
        return poidsMax;
    }

    public double getDimensionMax() {
        return dimensionMax;
    }

    /**
     * Vérifie si un produit est compatible avec ce type
     */
    public boolean peutContenir(double poids, double dimension) {
        return poids <= poidsMax && dimension <= dimensionMax;
    }
}