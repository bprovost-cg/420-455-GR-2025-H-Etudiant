package com.example.stockage;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.stockage.utilities.FileUtils;
import com.example.stockage.utilities.NotifUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class StockageExterneFragment extends Fragment {
    private EditText editTextNomFichier;
    private EditText editTextContenuFichier;
    private TextView editTextLoadContenuFichier;
    private ArrayAdapter<String> adapterInterne;
    private ArrayAdapter<String> adapterCache;

    public StockageExterneFragment() {
        super(R.layout.fragment_stockage_interne);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextNomFichier = view.findViewById(R.id.edit_text_nom_fichier);
        editTextContenuFichier = view.findViewById(R.id.edit_text_contenu_fichier);
        Button buttonCreerOuRemplacer = view.findViewById(R.id.button_creer_ou_remplacer);
        Button buttonCreerOuRemplacerCache = view.findViewById((R.id.button_creer_ou_remplacer_cache));
        Button buttonCreerOuAjouter = view.findViewById(R.id.button_creer_ou_ajouter);
        Button buttonCreerOuAjouterCache = view.findViewById((R.id.button_creer_ou_ajouter_cache));
        Button buttonLire = view.findViewById(R.id.button_lire);
        Button buttonLireCache = view.findViewById(R.id.button_lire_cache);

        editTextLoadContenuFichier = view.findViewById(R.id.text_view_lire_contenu_fichier);

        ListView listFichiers = view.findViewById(R.id.list_fichiers);

        var root = getFile("");
        adapterInterne = setupListViewAdapter(root, listFichiers);

        ListView listFichiersCache = view.findViewById(R.id.list_fichiers_cache);

        var cacheRoot = getCacheFile("");
        adapterCache = setupListViewAdapter(cacheRoot, listFichiersCache);

        buttonCreerOuRemplacer.setOnClickListener(v ->
        {
            File file = getFileFromEditText();
            String fileContent = editTextContenuFichier.getText().toString();
            createOrReplace(file, fileContent);
            refreshAdapter();
        });

        buttonCreerOuRemplacerCache.setOnClickListener(v ->
        {
            File file = getCacheFileFromEditText();
            String fileContent = editTextContenuFichier.getText().toString();
            createOrReplace(file, fileContent);
            refreshAdapterCache();
        });

        buttonCreerOuAjouter.setOnClickListener(v ->
        {
            File file = getFileFromEditText();
            String fileContent = editTextContenuFichier.getText().toString();
            createOrAppend(file, fileContent);
            refreshAdapter();
        });

        buttonCreerOuAjouterCache.setOnClickListener(v ->
        {
            File file = getCacheFileFromEditText();
            String fileContent = editTextContenuFichier.getText().toString();
            createOrAppend(file, fileContent);
            refreshAdapterCache();
        });

        buttonLire.setOnClickListener(v -> {
            File file = getFileFromEditText();
            readFile(file);
        });

        buttonLireCache.setOnClickListener(v -> {
            File file = getCacheFileFromEditText();
            readFile(file);
        });
    }

    private ArrayAdapter<String> setupListViewAdapter(File root, ListView listFichiers) {
        ArrayList<String> files = FileUtils.getDirFilesAsArrayList(root);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1, files);
        adapter.sort(Comparator.naturalOrder());
        listFichiers.setAdapter(adapter);

        listFichiers.setOnItemClickListener((parent, v, position, id) ->
        {
            String fileName = adapter.getItem(position);
            var file = new File(root, fileName);

            if (file.delete()) {
                adapter.remove(fileName);
                return;
            }

            NotifUtils.showToast(requireActivity(), String.format("Le fichier %s n'a pas pu être effacé", fileName));
        });

        return adapter;
    }

    private void createOrReplace(File file, String fileContent) {
        try {
            if (isExternalStorageWritable()) {
                FileUtils.createOrReplaceFile(file, fileContent);
            }
        } catch (IOException e) {
            Toast.makeText(requireActivity(), String.format("%s ne peut pas être créé ou modifié", file.getName()), Toast.LENGTH_SHORT).show();
        }
    }

    private void createOrAppend(File file, String fileContent) {
        try {
            if (isExternalStorageWritable()) {
                FileUtils.createOrAppendToFile(file, fileContent);
            }
        } catch (IOException e) {
            NotifUtils.showToast(requireActivity(), String.format("%s ne peut pas être créé ou modifié", file.getName()));
        }
    }

    private void readFile(File file) {
        try {
            if (isExternalStorageReadable()) {
                var content = FileUtils.readFile(file);
                editTextLoadContenuFichier.setText(content);
            }
        } catch (IOException e) {
            NotifUtils.showToast(requireActivity(), String.format("%s ne peut pas être lu", file.getName()));
        }
    }

    private void refreshAdapter() {
        var root = getFile("");
        ArrayList<String> files = FileUtils.getDirFilesAsArrayList(root);
        adapterInterne.clear();
        adapterInterne.addAll(files);
        adapterInterne.sort(Comparator.naturalOrder());

    }

    private void refreshAdapterCache() {
        var rootCache = getCacheFile("");
        ArrayList<String> files = FileUtils.getDirFilesAsArrayList(rootCache);
        adapterCache.clear();
        adapterCache.addAll(files);
        adapterCache.sort(Comparator.naturalOrder());
    }

    private File getFileFromEditText() {
        String fileName = editTextNomFichier.getText().toString();
        return getFile(fileName);
    }

    private File getCacheFileFromEditText() {
        String fileName = editTextNomFichier.getText().toString();
        return getCacheFile(fileName);
    }

    private File getFile(String fileName) {
        return new File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
    }

    private File getCacheFile(String fileName) {
        return new File(requireActivity().getExternalCacheDir(), fileName);
    }

    // Vérifier si le stockage externe est disponible en lecture/écriture
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Vérifier si le stockage externe est au moins disponible en lecture
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}