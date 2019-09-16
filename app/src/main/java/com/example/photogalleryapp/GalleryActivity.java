package com.example.photogalleryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GalleryActivity extends AppCompatActivity implements SearchInputFragment.OnInputListener{

    ImageButton button_back;
    Button button_searchPopup;
    TextView testing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide the title and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        //show the activity in full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_gallery);

        testing = findViewById(R.id.text_testing);

        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        button_searchPopup = findViewById(R.id.button_searchPopup);
        button_searchPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInputFragment searchInputFragment = new SearchInputFragment();
                searchInputFragment.show(getSupportFragmentManager(), "Search Fragment Popup");
            }
        });

    }

    @Override
    public void searchPhoto(String input) {
        testing.setText(input);
    }
}
