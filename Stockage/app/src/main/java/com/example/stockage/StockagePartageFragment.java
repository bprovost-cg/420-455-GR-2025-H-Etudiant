package com.example.stockage;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.stockage.utilities.FileUtils;
import com.example.stockage.utilities.NotifUtils;

public class StockagePartageFragment extends Fragment {
    private ImageView imageView;
    // Enregistre le sélecteur de média en mode "sélection unique"
    private final ActivityResultLauncher<PickVisualMediaRequest> pickPhotoLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri ->
    {
        // Appelé après une selection ou si l'utilisateur quitte le sélecteur
        if (uri != null) {
            imageView.setImageURI(uri);
        } else {
            NotifUtils.showToast(requireActivity(), "Aucune photo choisie");
        }
    });

    private Uri newImageUri;
    private final ActivityResultLauncher<Intent> photoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {

                            if (newImageUri != null) {
                                imageView.setImageURI(newImageUri);
                            }

                        }
                    });
    private final ActivityResultLauncher<String> requestPhotoPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->
            {
                if (isGranted) {
                    dispatchTakePictureIntent();
                } else {
                    NotifUtils.showToast(requireActivity(), "Si vous souhaitez autoriser, allez dans vos paramètres et autoriser manuellement");
                }
            });
    private Uri newVideoUri;
    private VideoView videoView;
    private final ActivityResultLauncher<PickVisualMediaRequest> pickVideoLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri ->
    {
        // Appelé après une selection ou si l'utilisateur quitte le sélecteur
        if (uri != null) {
            videoView.setVideoURI(uri);
            videoView.start();
        } else {
            NotifUtils.showToast(requireActivity(), "Aucune vidéo choisie");
        }
    });
    private final ActivityResultLauncher<Intent> videoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            if (newVideoUri != null) {
                                videoView.setVideoURI(newVideoUri);
                                videoView.start();
                            }
                        }
                    });
    private final ActivityResultLauncher<String> requestVideoPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->
            {
                if (isGranted) {
                    dispatchTakeVideoIntent();
                } else {
                    NotifUtils.showToast(requireActivity(), "Si vous souhaitez autoriser, allez dans vos paramètres et autoriser manuellement");
                }
            });

    public StockagePartageFragment() {
        super(R.layout.fragment_stockage_partage);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonTakePhoto = view.findViewById(R.id.button_take_photo);
        Button buttonTakeVideo = view.findViewById(R.id.button_take_video);
        Button buttonOpenPicker = view.findViewById(R.id.button_open_photo_picker);
        Button buttonOpenVideoPicker = view.findViewById(R.id.button_open_video_picker);

        imageView = view.findViewById(R.id.image_preview);
        videoView = view.findViewById(R.id.video_preview);

        buttonTakePhoto.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                dispatchTakePictureIntent();
            } else {
                requestPhotoPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        buttonTakeVideo.setOnClickListener(v -> {
            if (checkCameraPermission()) {
                dispatchTakeVideoIntent();
            } else {
                requestVideoPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });

        buttonOpenPicker.setOnClickListener(v -> photoPicker());
        buttonOpenVideoPicker.setOnClickListener(v -> videoPicker());
    }

    public void photoPicker() {
        pickPhotoLauncher.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()
        );
    }

    public void videoPicker() {
        pickVideoLauncher.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                .build()
        );
    }

    private boolean checkCameraPermission() {
        int permission = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Pour sauvegarder une image de pleine qualité on doit d'abord fournir un fichier
        var contentResolver = getActivity().getContentResolver();
        newImageUri = FileUtils.createMediaFileUri(contentResolver, "jpg", "image/jpeg");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, newImageUri);
        try {
            photoLauncher.launch(takePictureIntent);
        } catch (ActivityNotFoundException ex) {
            NotifUtils.showToast(requireActivity(), "Aucune activité pour prendre la photo");
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // Pour sauvegarder une image de pleine qualité on doit d'abord fournir un fichier
        var contentResolver = getActivity().getContentResolver();
        newVideoUri = FileUtils.createMediaFileUri(contentResolver, "3gp", "video/3gpp");
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, newVideoUri);
        try {
            videoLauncher.launch(takeVideoIntent);
        } catch (ActivityNotFoundException ex) {
            NotifUtils.showToast(requireActivity(), "Aucune activité pour prendre la vidéo");
        }
    }
}