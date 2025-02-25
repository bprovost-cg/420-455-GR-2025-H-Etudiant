package com.example.listeepicerie;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.liste_epicerie);

        ArrayList<Produit> liste = new ArrayList<Produit>()
        {{
            add(new Produit("Brocoli", "Un légume vert riche en nutriments"));
            add(new Produit("Carotte", "Un légume orange, doux et croquant"));
            add(new Produit("Chou-fleur", "Un légume blanc et moelleux"));
            add(new Produit("Poivron", "Un légumé coloré en rouge, vert, jaune ou orange, doux ou épicé"));
        }};

        ListView listView = findViewById(R.id.liste_epicerie);

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            String msg = liste.get(position).toString();
            toast(msg);
            view.setSelected(true);
        });

        ProduitAdapter adapter = new ProduitAdapter(this, R.layout.liste_item, liste, R.id.liste_item_nom, R.id.liste_item_description);

        listView.setAdapter(adapter);

        Button btnAjouter = findViewById(R.id.bouton_ajouter);
        EditText produit = findViewById(R.id.produit);
        btnAjouter.setOnClickListener(view -> {
            var text = produit.getText().toString();

            if (text.isBlank())
            {
                toast("Vous devez écrire un nom et une description");
                return;
            }

            var texts = produit.getText().toString().split("\\s", 2);
            // nom seulement
            if (texts.length == 1)
            {
                adapter.add(new Produit(texts[0], ""));
            }
            // nom et description
            else
            {
                adapter.add(new Produit(texts[0], texts[1]));
            }


        });
    }

    private void toast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}