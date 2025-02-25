package com.example.e4;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button photo_activity_button;
    private Button email_activity_button;
    private Button web_activity_button;

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

        photo_activity_button = findViewById(R.id.photo_activity_button);
        photo_activity_button.setOnClickListener(v -> goToActivity(PhotoActivity.class));
        email_activity_button = findViewById(R.id.email_activity_button);
        email_activity_button.setOnClickListener(v -> goToActivity(EmailActivity.class));
        web_activity_button = findViewById(R.id.web_activity_button);
        web_activity_button.setOnClickListener(v -> goToActivity(WebActivity.class));
    }

    private void goToActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            ActivityHelper.showToast(this, String.format(getString(R.string.error_activity_not_found), cls.toString()));
        }
    }


}