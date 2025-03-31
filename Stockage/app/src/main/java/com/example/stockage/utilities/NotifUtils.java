package com.example.stockage.utilities;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;

public class NotifUtils {

    public static void showToast(FragmentActivity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String message) {
        if (view == null) {
            throw new NullPointerException("View ne peut pas être null");
        }
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
