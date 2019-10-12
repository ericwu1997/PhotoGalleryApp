package com.example.photogalleryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.photogalleryapp.Adapter.RecyclerViewAdapter;
import com.example.photogalleryapp.Manager.PhotoDisplayManager;

import java.util.Date;

public class GalleryActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, SearchInputFragment.OnInputListener {

    ImageButton button_back;
    Button button_searchPopup;

    RecyclerViewAdapter adapter;
    PhotoDisplayManager photoDisplayManager;

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

        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        photoDisplayManager = PhotoDisplayManager.getInstance();

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycleView_thumbnail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, photoDisplayManager.getPhotoList());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void searchPhoto(String input) {
//        testing.setText(input);
    }

    @Override
    public void searchPhoto(Date startDate, Date endDate) {
//        testing.setText("");
    }

    @Override
    public void searchPhoto(String input, Date startDate, Date endDate) {
//        testing.setText("");
    }

    @Override
    public void onItemClick(View view, String caption) {
        photoDisplayManager.setCurrentIndexByName(caption);
        finish();
    }
}
