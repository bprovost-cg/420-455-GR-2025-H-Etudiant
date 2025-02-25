package com.example.e4;

import android.content.pm.PackageManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ActivityHelper {
    public static String getText(AppCompatActivity context, int id) throws NoSuchMethodException {
        View view = context.findViewById(id);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            return textView.getText().toString();
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            Editable editable = editText.getText();
            if (editable != null) {
                return editable.toString();
            }
        }

        throw new NoSuchMethodException("La vue obtenue avec l'id: " + id + " n'a pas de méthode getText()");
    }

    public static void setText(AppCompatActivity context, int id, String text) throws NoSuchMethodException {
        View view = context.findViewById(id);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(text);
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setText(text);
        }

        throw new NoSuchMethodException("La vue obtenue avec l'id: " + id + " n'a pas de méthode setText()");
    }

    public static void showToast(AppCompatActivity context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showMissingAppToast(AppCompatActivity context, String action) {
        showToast(context, "Pas d'application disponible pour l'action: " + action);
    }

    public static boolean checkPermission(AppCompatActivity context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            // Nous avons la permission
            return true;
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            // On explique pourquoi on a besoin de la permission;
            ActivityHelper.showToast(context, "Sans la permission: " + permission + " on ne peut pas faire l'action demandée");
            // On va demander la permission
            return false;
        } else {
            // On va demander la permission
            return false;
        }
    }

    public static void log(AppCompatActivity context, String message) {
        Log.d(context.getClass().getSimpleName(), message);
    }
}
