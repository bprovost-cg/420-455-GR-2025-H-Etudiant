package com.example.e2_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hello);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView label = findViewById(R.id.hello_label);
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                //erreur
            }
            else {
                String name = extras.getString("name");
                if (name != null && !name.isBlank())
                {
                    label.setText("Bonjour " + name + "!");
                }
                else
                {
                    //erreur
                }

            }
        }
        else {
            String name = (String) savedInstanceState.getSerializable("name");
            if (name != null || !name.isBlank())
            {
                label.setText("Bonjour " + name + "!");
            }
            else
            {

            }
        }
    }
}