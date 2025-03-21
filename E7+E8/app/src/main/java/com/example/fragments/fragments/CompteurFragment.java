package com.example.fragments.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.fragments.R;
import com.example.fragments.viewmodels.CompteurViewModel;

public class CompteurFragment extends Fragment {

    public CompteurFragment() {
        super(R.layout.fragment_compteur);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisation des vues
        TextView tvCompteur = view.findViewById(R.id.tv_compteur);
        Button btnIncrementer = view.findViewById(R.id.btn_incrementer);
        Button btnDecrementer = view.findViewById(R.id.btn_decrementer);
        Button btnReinitialiser = view.findViewById(R.id.btn_reinitialiser);
        Button btnHistorique = view.findViewById(R.id.btn_historique);

        // Récupération du ViewModel (partagé avec l'activité)
        CompteurViewModel viewModel = new ViewModelProvider(requireActivity()).get(CompteurViewModel.class);

        // Observation du LiveData du compteur
        viewModel
                .getCompteur()
                .observe(getViewLifecycleOwner(), compteur ->
                        tvCompteur.setText(String.valueOf(compteur))
                );

        // Configuration des boutons
        btnIncrementer.setOnClickListener(v -> viewModel.incrementer());

        btnDecrementer.setOnClickListener(v -> viewModel.decrementer());

        btnReinitialiser.setOnClickListener(v -> viewModel.reinitialiser());

        btnHistorique.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_compteurFragment_to_historiqueFragment)
        );
    }
}