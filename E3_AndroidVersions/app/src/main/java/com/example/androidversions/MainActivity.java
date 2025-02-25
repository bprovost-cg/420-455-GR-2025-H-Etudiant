package com.example.androidversions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView versionList;
    ArrayList<AndroidVersion> versions;

    AndroidVersionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        versionList = findViewById(R.id.version_list);

        versions = new ArrayList<>() {{
            add(new AndroidVersion("Android 16", "Baklava", "36"));
            add(new AndroidVersion("Android 15", "Vanilla Ice Cream", "35"));
            add(new AndroidVersion("Android 14", "Upside Down Cake", "34"));
            add(new AndroidVersion("Android 13", "Tiramisu", "33"));
        }};

        adapter = new AndroidVersionAdapter(this, R.layout.android_version, versions);

        versionList.setAdapter(adapter);

        registerForContextMenu(versionList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Toujours avec le if/else-if plutôt qu'un switch
        if (id == R.id.menu_add) {
            createAddOrEditDialog(null);
        } else if (id == R.id.menu_clear) {
            // Méthode via le ArrayList
            // versions.clear();
            // adapter.notifyDataSetChanged();

            // Méthode via l'adapteur (recommandée)
            adapter.clear();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextual_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemId = item.getItemId();
        AndroidVersion version = versions.get(info.position);
        if (itemId == R.id.contextual_edit) {
            createAddOrEditDialog(version);
            return true;
        } else if (itemId == R.id.contextual_delete) {
            createDeleteDialog(version);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    // Pas obligatoire de combiner dans la même méthode et vue
    public void createAddOrEditDialog(AndroidVersion androidVersion) {
        Dialog dialog = new Dialog(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_or_edit_version, null);
        dialog.setContentView(view);
        dialog.show();

        // IMPORTANT: vous devez faire findViewById sur la view et pas sur this
        // sinon vous aurez une erreur obscure en arrivant à ces lignes
        TextView title = view.findViewById(R.id.dialog_title);
        EditText nameText = view.findViewById(R.id.name_text);
        EditText codeText = view.findViewById(R.id.code_text);
        EditText versionText = view.findViewById(R.id.version_text);
        Button createOrModifyButton = view.findViewById(R.id.create_or_modify_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);

        if (androidVersion == null) {
            title.setText(R.string.add_dialog_title);
            createOrModifyButton.setText(R.string.create);

        } else {
            title.setText(R.string.edit_dialog_title);
            createOrModifyButton.setText(R.string.modify);
            nameText.setText(androidVersion.Name);
            codeText.setText(androidVersion.CodeName);
            versionText.setText(androidVersion.ApiVersion);

        }

        createOrModifyButton.setOnClickListener(v -> {
            String name = nameText.getText().toString();
            String code = codeText.getText().toString();
            String version = versionText.getText().toString();

            if (androidVersion == null) {
                // Méthode via le ArrayList
                // versions.add(new AndroidVersion(name, code, version));
                //adapter.notifyDataSetChanged();

                // Méthode via l'adapteur (recommandée)
                adapter.add(new AndroidVersion(name, code, version));
            } else {
                // Méthode via le ArrayList
                // int pos = versions.indexOf(androidVersion);
                // versions.set(pos, new AndroidVersion(name, code, version));
                // adapter.notifyDataSetChanged();

                // Méthode via l'adapteur (recommandée)
                int pos = adapter.getPosition(androidVersion);
                adapter.remove(androidVersion);
                adapter.insert(new AndroidVersion(name, code, version), pos);
            }
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.cancel());
    }

    public void createDeleteDialog(AndroidVersion androidVersion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Voulez-vous vraiment effacer la version " + androidVersion.Name + "?")
                // Méthode via le ArrayList
                //.setPositiveButton("Oui", (dialog, id) -> {
                //    versions.remove(androidVersion);
                //    adapter.notifyDataSetChanged();
                //})
                // Méthode via l'adapteur (recommandée)
                .setPositiveButton("Oui", (dialog, id) -> adapter.remove(androidVersion))
                .setNegativeButton("Non", (dialog, id) -> dialog.cancel())
                .show();
    }

}