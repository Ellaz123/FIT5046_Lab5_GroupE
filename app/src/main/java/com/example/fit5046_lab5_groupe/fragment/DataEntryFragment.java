package com.example.fit5046_lab5_groupe.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.example.fit5046_lab5_groupe.databinding.DataEntryFragmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DataEntryFragment extends Fragment {
    private DataEntryFragmentBinding dataBinding;
    private DatePickerDialog datePickerDialog;
    private Button dateBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBinding = DataEntryFragmentBinding.inflate(inflater, container, false);
        View view = dataBinding.getRoot();
        myDatePicker();
        dateBtn = dataBinding.dateBtn;
        dateBtn.setText(getCurrentDate());
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDatePicker(view);

            }
        });

        return view;

    }

    private void myDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener =  new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = generateDate(day, month, year);
                String currentDate = getCurrentDate();
                try {
                    Date selectDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    Date now =  new SimpleDateFormat("dd/MM/yyyy").parse(currentDate);
                    if(selectDate.before(now))
                    {
                        dateBtn.setText("Invalid Date");
                    }
                    else
                    {
                        dateBtn.setText(date);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        };

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int theme = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(getActivity(), theme,dateSetListener, day, month, year);

    }

    private String generateDate(int day, int month, int year)
    {
        String date = day + "/" + month + "/" + year;

        return date;
    }

    private String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int year = calendar.get(Calendar.YEAR);

        return generateDate(day, month, year);
    }

    public void showDatePicker(View view)
    {
        datePickerDialog.show();
    }
}
