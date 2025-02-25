package com.example.e4;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PhotoActivity extends AppCompatActivity {

    private Button photoButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_photo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        photoButton = findViewById(R.id.photo_button);

        photoButton.setOnClickListener(v ->
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (!ActivityHelper.checkPermission(this, Manifest.permission.CAMERA)) {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }

            try {
                cameraActivityResultLauncher.launch(intent);
            } catch (ActivityNotFoundException ex) {
                ActivityHelper.showMissingAppToast(this, MediaStore.ACTION_IMAGE_CAPTURE);
            }
        });
        imageView = findViewById(R.id.image_view);
    }

    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            activityResult -> {
                if (activityResult.getResultCode() == RESULT_OK) {
                    Intent intent = activityResult.getData();

                    // Thumbnail
                    Bitmap thumbnail = intent.getParcelableExtra("data");
                    imageView.setImageBitmap(thumbnail);
                } else {
                    ActivityHelper.showToast(this, "Requête annulée ou erreur");
                }
            }
    );

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    ActivityHelper.showToast(this, "Merci d'avoir autorisé, veuillez cliquer de nouveau");
                } else {
                    ActivityHelper.showToast(this, "Si vous souhaitez autoriser, vous devez aller dans vos paramètres et autoriser manuellement");
                }
            });
}