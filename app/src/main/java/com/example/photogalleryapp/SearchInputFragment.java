package com.example.photogalleryapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.photogalleryapp.Utils.DateParser;

import java.util.Calendar;
import java.util.Date;

public class SearchInputFragment extends DialogFragment {

    private final Calendar startDate_calender = Calendar.getInstance();
    private final Calendar endDate_calender = Calendar.getInstance();

    private Button button_confirm;

    private EditText text_keyword;
    private EditText text_dateStart;
    private EditText text_dateEnd;

    private OnInputListener onInputListener;

    public interface OnInputListener {
        void searchPhoto(String input, Date startDate, Date endDate);
    }

    private DatePickerDialog.OnDateSetListener startDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            startDate_calender.set(Calendar.YEAR, year);
            startDate_calender.set(Calendar.MONTH, monthOfYear);
            startDate_calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            text_dateStart.setText(DateParser.parseDate(startDate_calender.getTime()));
        }

    };

    private DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            endDate_calender.set(Calendar.YEAR, year);
            endDate_calender.set(Calendar.MONTH, monthOfYear);
            endDate_calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            text_dateEnd.setText(DateParser.parseDate(endDate_calender.getTime()));
        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_search_input_popup, container, false);

        text_keyword = view.findViewById(R.id.text_keyword);
        text_dateStart = view.findViewById(R.id.text_dateStart);
        text_dateEnd = view.findViewById(R.id.text_dateEnd);

        startDate_calender.set(Calendar.HOUR_OF_DAY, 0);
        startDate_calender.set(Calendar.MINUTE, 0);
        startDate_calender.set(Calendar.SECOND, 0);
        endDate_calender.set(Calendar.HOUR_OF_DAY, 23);
        endDate_calender.set(Calendar.MINUTE, 59);
        endDate_calender.set(Calendar.SECOND, 59);

        button_confirm = view.findViewById(R.id.button_confirm);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInputListener.searchPhoto(
                        text_keyword.getText().toString(),
                        startDate_calender.getTime(),
                        endDate_calender.getTime());
                getDialog().dismiss();
            }
        });

        text_dateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(view.getContext(), startDatePicker, startDate_calender
                        .get(Calendar.YEAR), startDate_calender.get(Calendar.MONTH),
                        startDate_calender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        text_dateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(view.getContext(), endDatePicker, endDate_calender
                        .get(Calendar.YEAR), endDate_calender.get(Calendar.MONTH),
                        endDate_calender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e("onAttach", "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}
