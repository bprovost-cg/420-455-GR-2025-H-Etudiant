package com.example.listeepicerie;

import androidx.annotation.NonNull;

public class Produit {
    private String nom;

    public String getNom() { return nom; }

    public void setNom(String nouveauNom) { this.nom = nouveauNom; }

    private String description;

    public String getDescription() { return description; }

    public void setDescription(String nouvelleDescription) { this.description = nouvelleDescription; }

    public Produit(String nom, String description)
    {
        setNom(nom);
        setDescription(description);
    }

    @NonNull
    @Override
    public String toString() {
        return this.nom + " - " + this.description;
    }
}
