package com.example.e2_3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText value1Text = findViewById(R.id.value1_text);
        EditText value2Text = findViewById(R.id.value2_text);
        EditText resultText = findViewById(R.id.result_text);

        Button addButton = findViewById(R.id.add_button);
        Button timesButton = findViewById(R.id.times_button);
        Button resetButton = findViewById(R.id.reset_button);

        addButton.setOnClickListener(view ->
        {
            Double value1 = parseValue(value1Text);
            Double value2 = parseValue(value2Text);
            Double result = value1 + value2;
            resultText.setText(result.toString());
        });

        timesButton.setOnClickListener(view ->
        {
            Double value1 = parseValue(value1Text);
            Double value2 = parseValue(value2Text);
            Double result = value1 * value2;
            resultText.setText(result.toString());
        });

        resetButton.setOnClickListener(view ->
        {
            value1Text.setText("");
            value2Text.setText("");
            resultText.setText("");
        });
    }

    private Double parseValue(EditText valueText)
    {
        try {
            Double value = Double.parseDouble(valueText.getText().toString());
            return value;
        }
        catch (NumberFormatException e)
        {
            return Double.NaN;
        }
    }
}