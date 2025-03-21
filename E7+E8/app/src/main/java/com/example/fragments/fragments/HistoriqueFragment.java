package com.example.fragments.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fragments.R;
import com.example.fragments.viewmodels.CompteurViewModel;
import com.example.fragments.viewmodels.Operation;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueFragment extends Fragment {
    private CompteurViewModel viewModel;
    private ListView lvHistorique;
    private TextView tvCompteurValeur;

    public HistoriqueFragment() {
        super(R.layout.fragment_historique);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisation des vues
        lvHistorique = view.findViewById(R.id.lv_historique);
        tvCompteurValeur = view.findViewById(R.id.tv_compteur_valeur);

        // Récupération du ViewModel (partagé avec l'activité)
        viewModel = new ViewModelProvider(requireActivity()).get(CompteurViewModel.class);

        // Observation du LiveData du compteur
        viewModel.getCompteur()
                .observe(getViewLifecycleOwner(), compteur ->
                        tvCompteurValeur.setText(String.valueOf(compteur))
                );

        // Observation du LiveData de l'historique
        viewModel.getHistorique()
                .observe(getViewLifecycleOwner(), operations ->
                {
                    List<String> operationsStr = new ArrayList<>();
                    for (Operation op : operations) {
                        String opStr = String.format(getString(R.string.historique_operation), op.heure, getString(viewModel.getOperationResId(op.type)), op.valeurResultat);
                        operationsStr.add(opStr);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            requireContext(),
                            R.layout.item_historique,
                            operationsStr
                    );

                    lvHistorique.setAdapter(adapter);
                });
    }
}