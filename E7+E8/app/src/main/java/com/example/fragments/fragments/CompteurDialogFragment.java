package com.example.fragments.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.fragments.R;
import com.example.fragments.viewmodels.Operation;

public class CompteurDialogFragment extends DialogFragment {
    public static String TAG = "CompteurDialogFragment";
    private final Operation operation;

    public CompteurDialogFragment(Operation operation) {
        this.operation = operation;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(String.format(getString(R.string.dialog_operation), operation.heure, operation.valeurResultat))
                .setPositiveButton(getString(R.string.action_ok), (dialog, which) -> {
                })
                .create();
    }
}
