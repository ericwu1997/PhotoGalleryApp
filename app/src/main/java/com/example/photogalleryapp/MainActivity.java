package com.example.photogalleryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.photogalleryapp.Manager.CameraManager;
import com.example.photogalleryapp.Manager.PhotoDisplayManager;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.sql.Timestamp;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PhotoDisplayManager photoDisplayManager;
    private CameraManager cameraManager;
    private ArrayList<String> photoGallery;
    private String currentPhotoPath=null;
    private int currentPhotoIndex=0;

    //Testing
    ImageView image_photoDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the title and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        //show the activity in full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Initialize manager classes
        cameraManager = new CameraManager(this);
        photoDisplayManager = new PhotoDisplayManager();

        // Search button
        ImageButton button_search = findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GalleryActivity.class);
                startActivity(intent);
            }
        });

        // Snap button
        ImageButton button_snap = findViewById(R.id.button_snap);
        button_snap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraManager.dispatchTakePictureIntent();
            }
        });

        // PhotoDisplay imageView
        //image_photoDisplay = findViewById(R.id.image_photoDisplay);
        ImageButton btnLeft = findViewById(R.id.button_left);
        ImageButton btnRight = findViewById(R.id.button_right);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        Date minDate = new Date(Long.MIN_VALUE);
        Date maxDate = new Date(Long.MAX_VALUE);
        photoGallery = populateGallery(minDate, maxDate);
        Log.d("onCreate, size", Integer.toString(photoGallery.size()));
        if (photoGallery.size() > 0)
            currentPhotoPath = photoGallery.get(currentPhotoIndex);
        displayPhoto(currentPhotoPath);
    }



    private ArrayList<String> populateGallery(Date minDate, Date maxDate) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.photogalleryapp/files/Pictures");
        ArrayList<String> photoGallery = new ArrayList<String>();
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : file.listFiles()) {
                photoGallery.add(f.getPath());
            }
        }
        return photoGallery;
    }

    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.image_photoDisplay);
        iv.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    public void onClick( View v) {
        switch (v.getId()) {
            case R.id.button_left:
                --currentPhotoIndex;
                break;
            case R.id.button_right:
                ++currentPhotoIndex;
                break;
            default:
                break;
        }
        if (currentPhotoIndex < 0)
            currentPhotoIndex = photoGallery.size() - 1;
        if (currentPhotoIndex >= photoGallery.size())
            currentPhotoIndex = 0;

        currentPhotoPath = photoGallery.get(currentPhotoIndex);
        Log.d("phpotoleft, size", Integer.toString(photoGallery.size()));
        Log.d("photoleft, index", Integer.toString(currentPhotoIndex));
        displayPhoto(currentPhotoPath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Display photo upon picture taken
        if (requestCode == CameraManager.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap temp;
            if ((temp = cameraManager.getLastTakenPicture()) != null) {
                image_photoDisplay.setImageBitmap(temp);
            }
        }
    }

}
