package com.example.fragments.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fragments.R;

import java.util.ArrayList;
import java.util.List;

public class CompteurViewModel extends ViewModel {
    // LiveData pour le compteur
    private final MutableLiveData<Integer> compteur = new MutableLiveData<>();

    // LiveData pour l'historique des opérations
    private final MutableLiveData<List<Operation>> historique = new MutableLiveData<>();

    // Constructeur
    public CompteurViewModel() {
        // Initialisation des valeurs
        compteur.setValue(0);
        historique.setValue(new ArrayList<>());
    }

    // Getter pour compteur (en tant que LiveData pour l'observer)
    public LiveData<Integer> getCompteur() {
        return compteur;
    }

    // Getter pour historique
    public LiveData<List<Operation>> getHistorique() {
        return historique;
    }

    // Méthode pour incrémenter le compteur
    public void incrementer() {
        int valeurActuelle = compteur.getValue() != null ? compteur.getValue() : 0;
        int nouvelleValeur = valeurActuelle + 1;
        compteur.setValue(nouvelleValeur);

        // Ajout dans l'historique
        ajouterOperation(OperationType.Incrementer, nouvelleValeur);
    }

    // Méthode pour décrémenter le compteur
    public void decrementer() {
        int valeurActuelle = compteur.getValue() != null ? compteur.getValue() : 0;
        int nouvelleValeur = valeurActuelle - 1;
        compteur.setValue(nouvelleValeur);

        // Ajout dans l'historique
        ajouterOperation(OperationType.Decrementer, nouvelleValeur);
    }

    // Méthode pour réinitialiser le compteur
    public void reinitialiser() {
        compteur.setValue(0);

        // Ajout dans l'historique
        ajouterOperation(OperationType.Reinitialiser, 0);
    }

    // Méthode pour ajouter une opération à l'historique
    private void ajouterOperation(OperationType type, int valeurResultante) {
        List<Operation> listeActuelle = historique.getValue();
        if (listeActuelle == null) {
            listeActuelle = new ArrayList<>();
        }

        listeActuelle.add(new Operation(type, valeurResultante));
        historique.setValue(listeActuelle);
    }

    public int getOperationResId(OperationType operationType) {
        switch (operationType) {
            case Incrementer:
                return R.string.operation_incrementation;
            case Decrementer:
                return R.string.operation_decrementation;
            case Reinitialiser:
                return R.string.operation_reinitialisation;
            default:
                return R.string.operation_invalid;
        }
    }
}