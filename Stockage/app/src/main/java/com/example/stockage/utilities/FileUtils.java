package com.example.stockage.utilities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.fragment.app.FragmentActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileUtils {

    public static void createOrReplaceFile(File file, String fileContents) throws IOException {
        createOrAppendToFile(file, fileContents, false);
    }

    public static void createOrAppendToFile(File file, String fileContents) throws IOException {
        createOrAppendToFile(file, fileContents, true);
    }

    public static String readFile(File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fis);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while (line != null) {
            stringBuilder.append(line).append('\n');
            line = reader.readLine();
        }
        fis.close();

        return stringBuilder.toString();
    }

    public static ArrayList<String> getDirFilesAsArrayList(File dir) {
        if (dir != null && dir.isDirectory()) {
            var list = dir.list();
            if (list != null) {
                List<String> fileList = Arrays.asList(list);
                return new ArrayList<>(fileList);
            }
        }
        return new ArrayList<>();
    }

    public static Uri createMediaFileUri(ContentResolver contentResolver, String fileExtension, String mimeType) {
        // Générer un nom de fichier unique basé sur la date/heure
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String mediaFileName = fileExtension.toUpperCase() + "_" + timeStamp + "." + fileExtension;

        var splitMimeType = mimeType.split("/");

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, mediaFileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);

        if (splitMimeType[0].equals("image")) {
            // Configuration des métadonnées de l'image
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10 (API 29) et plus récent
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            }

            return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else if (splitMimeType[0].equals("video")) {
            // Configuration des métadonnées de la vidéo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10 (API 29) et plus récent
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES);
            }

            return contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
        }

        throw new IllegalStateException("Mime-type invalide");
    }

    private static void createOrAppendToFile(File file, String fileContents, boolean append) throws IOException {
        FileOutputStream fos = new FileOutputStream(file, append);
        fos.write(fileContents.getBytes());
        fos.close();

    }
}
