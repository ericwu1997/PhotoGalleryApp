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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photogalleryapp.Manager.CameraManager;
import com.example.photogalleryapp.Manager.PhotoDisplayManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.ArrayList;
import java.sql.Timestamp;


public class MainActivity extends AppCompatActivity {
    private PhotoDisplayManager photoDisplayManager;
    private CameraManager cameraManager;

    private ImageView image_photoDisplay;
    EditText editCaption;
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
        photoDisplayManager = PhotoDisplayManager.getInstance();

        // Main image display
        image_photoDisplay = findViewById(R.id.image_photoDisplay);
        Bitmap temp;
        if ((temp = photoDisplayManager.getNextPhoto()) != null)
            image_photoDisplay.setImageBitmap(temp);

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

        //getFileName();
        editCaption = findViewById(R.id.text_caption);
        //Caption
        Button changeButton = findViewById(R.id.change_caption);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editCaption.getText().toString();
                changeFileName(content);
            }
        });


        // Left button
        ImageButton button_left = findViewById(R.id.button_left);
        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeFileName();
                getFileName();
                Bitmap temp;
                if ((temp = photoDisplayManager.getPrevPhoto()) != null)
                    image_photoDisplay.setImageBitmap(temp);
            }
        });

        // Right button
        ImageButton button_right = findViewById(R.id.button_right);
        button_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changeFileName();
                getFileName();
                Bitmap temp;
                if ((temp = photoDisplayManager.getNextPhoto()) != null)
                    image_photoDisplay.setImageBitmap(temp);
            }
        });

        // Display caption

    }

    protected void getFileName(){
        String photoCaption=photoDisplayManager.getFileName();
        TextView caption = findViewById(R.id.text_timeStamp);
        caption.setText(photoCaption);
    }

    protected void changeFileName(String input){

        File pPath = photoDisplayManager.getPath();
        String fFullName = pPath.getName();
        Log.d("Current path", pPath.toString());
        //String fName = fFullName.split("\\.")[0];
        //Path source =
        File from = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/Android/data/com.example.photogalleryapp/files/Pictures"+fFullName);
        //File from      = new File("/Android/data/com.example.photogalleryapp/files/Pictures", fFullName);
        File to        = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/Android/data/com.example.photogalleryapp/files/Pictures"+input+".jpg");

        Log.d("From path", from.toString());
        Log.d("Too path", to.toString());
        to.delete();
    from.renameTo(to);

//        Path souce = photoDisplayManager.getPath().toPath();
//        try {
//            Files.move(souce, souce.resolveSibling(input));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        TextView caption = findViewById(R.id.text_timeStamp);
        caption.setText(input);
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
