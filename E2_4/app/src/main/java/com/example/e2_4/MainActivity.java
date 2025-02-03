package com.example.e2_4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {
    private Double previous_value = null;
    private Double new_value = null;
    private Operation last_operation = Operation.None;

    private enum Operation {
        None,
        Addition,
        Substraction,
        Multiplication,
        Division,
        Equals
    }
    private TextView result_label = null;

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

        result_label = findViewById(R.id.result_label);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button0 = findViewById(R.id.button0);

        Button button_plus = findViewById(R.id.button_plus);
        Button button_minus = findViewById(R.id.button_minus);
        Button button_times = findViewById(R.id.button_times);
        Button button_divide = findViewById(R.id.button_divide);

        Button button_reset = findViewById(R.id.button_reset);
        Button button_equal = findViewById(R.id.button_equal);

        button0.setOnClickListener(view -> addDigit(0d));
        button1.setOnClickListener(view -> addDigit(1d));
        button2.setOnClickListener(view -> addDigit(2d));
        button3.setOnClickListener(view -> addDigit(3d));
        button4.setOnClickListener(view -> addDigit(4d));
        button5.setOnClickListener(view -> addDigit(5d));
        button6.setOnClickListener(view -> addDigit(6d));
        button7.setOnClickListener(view -> addDigit(7d));
        button8.setOnClickListener(view -> addDigit(8d));
        button9.setOnClickListener(view -> addDigit(9d));

        button_plus.setOnClickListener(view -> operation(Operation.Addition));
        button_minus.setOnClickListener(view -> operation(Operation.Substraction));
        button_times.setOnClickListener(view -> operation(Operation.Multiplication));
        button_divide.setOnClickListener(view -> operation(Operation.Division));

        button_equal.setOnClickListener(view -> operation(Operation.Equals));
        button_reset.setOnClickListener(view -> reset());
    }

    private void reset()
    {
        new_value = null;
        previous_value = null;
        last_operation = Operation.None;
        displayNumber(0d);
    }

    private void operation(Operation operation) {
        // No values have been entered, do nothing
        if (new_value == null && previous_value == null)
        {
            return;
        }

        // There was a previous value, but no new one. Meaning another operation was done, change last_operation
        if (new_value == null)
        {
            last_operation = operation;
            return;
        }

        // This is the first time we use an operation, store the value, note the last_operation and reset the new_value
        if (previous_value == null)
        {
            previous_value = new_value;
            new_value = null;
            last_operation = operation;
            return;
        }

        // Calculate last operation
        Double result = calculateValue(last_operation);

        if (operation == Operation.Equals) {
            // Display result only when equals is pressed
            displayNumber(result);
            last_operation = Operation.None;
            previous_value = result;
            new_value = null;
        }
        else
        {
            last_operation = operation;
            previous_value = result;
            new_value = null;
        }
    }

    private Double calculateValue(Operation operation) {
        Double result = 0d;
        switch (operation) {
            case Equals:
                result = new_value;
                break;
            case Addition:
                result = previous_value + new_value;
                break;
            case Substraction:
                result = previous_value - new_value;
                break;
            case Multiplication:
                result = previous_value * new_value;
                break;
            case Division:
                result = previous_value / new_value;
                break;
        }
        return result;
    }

    private void addDigit(Double digit)
    {
        // Start new value if it's empty
        if (new_value == null)
        {
            new_value = digit;
        }
        else
        {
            new_value = new_value * 10 + digit;
        }
        displayNumber(new_value);
    }

    private void displayNumber(Double value)
    {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setMaximumFractionDigits(3);
        result_label.setText(nf.format(value));
    }

}