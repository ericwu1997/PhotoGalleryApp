package com.example.photogalleryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.photogalleryapp.Manager.PhotoDisplayManager;
import com.example.photogalleryapp.Utils.DateParser;

import java.util.Calendar;


public class GalleryActivity extends AppCompatActivity {

    final Calendar calender_startDate = Calendar.getInstance();
    final Calendar calender_endDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate_onClickListener;
    DatePickerDialog.OnDateSetListener endDate_onClickListener;

    ImageButton button_back;
    Button button_confirm;
    EditText text_keyword;
    EditText text_startDate;
    EditText text_endDate;
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

        // PhotoDisplayManager
        photoDisplayManager = PhotoDisplayManager.getInstance();

        // text_keyword
        text_keyword = findViewById(R.id.text_keyword);

        // Start Date picker
        calender_startDate.set(Calendar.HOUR_OF_DAY, 0);
        calender_startDate.set(Calendar.MINUTE, 0);
        calender_startDate.set(Calendar.SECOND, 0);

        startDate_onClickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calender_startDate.set(Calendar.YEAR, year);
                calender_startDate.set(Calendar.MONTH, monthOfYear);
                calender_startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                text_startDate.setText(DateParser.parseDate(calender_startDate.getTime()));
            }

        };

        text_startDate = findViewById(R.id.text_dateStart);
        text_startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), startDate_onClickListener,
                        calender_startDate.get(Calendar.YEAR),
                        calender_startDate.get(Calendar.MONTH),
                        calender_startDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // End Date picker
        calender_endDate.set(Calendar.HOUR_OF_DAY, 23);
        calender_endDate.set(Calendar.MINUTE, 59);
        calender_endDate.set(Calendar.SECOND, 59);

        endDate_onClickListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calender_endDate.set(Calendar.YEAR, year);
                calender_endDate.set(Calendar.MONTH, monthOfYear);
                calender_endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                text_endDate.setText(DateParser.parseDate(calender_endDate.getTime()));
            }

        };

        text_endDate = findViewById(R.id.text_dateEnd);
        text_endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), endDate_onClickListener,
                        calender_endDate.get(Calendar.YEAR),
                        calender_endDate.get(Calendar.MONTH),
                        calender_endDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // return to main button
        button_back = findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoDisplayManager.removeFilter();
                finish();
            }
        });

        button_confirm = findViewById(R.id.button_confirm);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = text_keyword.getText().toString();
                photoDisplayManager
                        .setFilter(keyword.equals("") ? null : keyword,
                                calender_startDate.getTime(),
                                calender_endDate.getTime());
                finish();
            }
        });
    }


}
