package com.example.e2_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class NameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_name);

        EditText editText = findViewById(R.id.name_edit_text);

        Button button = findViewById(R.id.name_button);

        button.setOnClickListener(view ->
        {
            String name = String.valueOf(editText.getText());

            // Le nom doit avoir une valeur
            if(name != null && !name.isBlank())
            {
                Intent activity = new Intent(NameActivity.this, MainActivity.class);

                activity.putExtra("name", name.trim());

                startActivity(activity);
            }
            else
            {
                //Si le nom est null, vide ou que des espaces alors on affiche un message
                //Toast.makeText(this, R.string.enter_name, Toast.LENGTH_LONG).show();

                Snackbar.make(findViewById(R.id.name_button), R.string.enter_name, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
