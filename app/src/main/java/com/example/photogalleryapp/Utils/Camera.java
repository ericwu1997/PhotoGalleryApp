package com.example.photogalleryapp.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.example.photogalleryapp.Utils.Photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Camera {
    public static final int REQUEST_TAKE_PHOTO = 1;

    private Activity activity;
    private File sdCardDirectory;
    private String currentPhotoPath;

    public Camera(Activity activity) {
        this.activity = activity;
        sdCardDirectory = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("FILE CREATION FAILED", ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity,
                        "com.example.android.photogalleryapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "UNTITLED_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                sdCardDirectory      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public Photo getLastTakenPicture() {
        Uri imageUri = Uri.parse(currentPhotoPath);
        try {
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);

                return new Photo(file.getName(), BitmapFactory.decodeStream(ims), new Date(file.lastModified()));
            } catch (FileNotFoundException e) {
                return null;
            }
        } catch (NullPointerException ex) {
            Log.e("FILE PATH NOT EXIST", ex.toString());
        }
        return null;
    }

    static public class FileNameParser {
        public static String parse(String fullName) {
            if (fullName.contains("_")) {
                int i = fullName.lastIndexOf('_');
                return fullName.substring(0, i);
            }
            return null;
        }

        public static String parseUniqueID(String fullName) {
            if (fullName.contains("_")) {
                int i = fullName.lastIndexOf('_');
                return fullName.substring(i);
            }
            return null;
        }
    }
}
