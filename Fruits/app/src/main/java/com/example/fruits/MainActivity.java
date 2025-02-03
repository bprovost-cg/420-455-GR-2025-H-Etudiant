package com.example.fruits;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        ListView listView = findViewById(R.id.fruit_list);

        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit("Pomme", "Rouge"));
        fruitList.add(new Fruit("Banane", "Jaune"));
        fruitList.add(new Fruit("Cerise", "Rouge"));

        // Utiliser l'Adapter personnalisé
        FruitAdapter fruitAdapter = new FruitAdapter(this, fruitList);
        listView.setAdapter(fruitAdapter);
        listView.setOnItemClickListener((parent, view, position, id) ->
        {
            //
        });
    }
}