package com.example.e4;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EmailActivity extends AppCompatActivity {
    Button sendButton;

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

        sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(v -> sendMail());
    }

    private void sendMail() {

        Intent intent = createIntent();

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.chooser_send_with)));
        } catch (ActivityNotFoundException ex) {
            ActivityHelper.showMissingAppToast(this, Intent.ACTION_SENDTO);
            Toast.makeText(this, R.string.error_no_mail_client_available, Toast.LENGTH_LONG).show();
        }
    }

    private Intent createIntent() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, getTextFrom(R.id.to_text));
        intent.putExtra(Intent.EXTRA_SUBJECT, getTextFrom(R.id.subject_text));
        intent.putExtra(Intent.EXTRA_TEXT, getTextFrom(R.id.message_text));
        return intent;
    }

    private String getTextFrom(int id) {
        EditText editText = findViewById(id);
        Editable editable = editText.getText();
        if (editable != null) {
            return editable.toString();
        }
        return null;
    }
}