package com.example.e4;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReceiveMailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_email);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        intent.getData();
        String[] addresses = intent.getStringArrayExtra(Intent.EXTRA_EMAIL);
        String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        String message = intent.getStringExtra(Intent.EXTRA_TEXT);
        try {
            ActivityHelper.setText(this, R.id.to_text, String.join(";", addresses));
            ActivityHelper.setText(this, R.id.subject_text, subject);
            ActivityHelper.setText(this, R.id.message_text, message);
        } catch (NoSuchMethodException e) {
            ActivityHelper.log(this, e.getMessage());
        }
    }

}