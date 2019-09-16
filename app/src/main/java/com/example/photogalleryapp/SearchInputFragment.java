package com.example.photogalleryapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.util.Date;

public class SearchInputFragment extends DialogFragment {

    private Button button_confirm;
    private OnInputListener onInputListener;

    public interface OnInputListener{
        void searchPhoto(String input);
        void searchPhoto(Date date, Time time);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_search_input_popup, container,false);

        button_confirm = view.findViewById(R.id.button_confirm);
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInputListener.searchPhoto("SUCCESS");
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e("onAttach", "onAttach: ClassCastException: " + e.getMessage());
        }
    }
}
