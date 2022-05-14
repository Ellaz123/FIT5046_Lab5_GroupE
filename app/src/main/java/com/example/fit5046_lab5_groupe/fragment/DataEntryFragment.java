package com.example.fit5046_lab5_groupe.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fit5046_lab5_groupe.R;
import com.example.fit5046_lab5_groupe.databinding.DataEntryFragmentBinding;
import com.example.fit5046_lab5_groupe.entity.Order;
import com.example.fit5046_lab5_groupe.viewmodel.OrderViewModel;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DataEntryFragment extends Fragment {
    private DataEntryFragmentBinding dataBinding;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateBtn;
    private  Button timeBtn;
    private SeekBar seekBar;
    private TextView textView;
    private TextView textViewRadio;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText name;
    private EditText phone;
    private  Button button;

    private int hour, minute;
    private int numRats;

    private String orderDate, orderTime, numRat, ratType, oName, oPhone, orderInfo;

    private OrderViewModel orderViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBinding = DataEntryFragmentBinding.inflate(inflater, container, false);
        View view = dataBinding.getRoot();

        orderViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).
                create(OrderViewModel.class);

        orderViewModel.getAllCustomers().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                String allOrders = "";
                int count = 1;
                for(Order temp: orders){
                    String orderDetails = (count + " " + temp.orderDate+" "+temp.orderTime+" "
                            +temp.numRat + " " + temp.ratType + " " + temp.customerName + " " +
                            temp.customerPhone);
                    allOrders = allOrders + System.getProperty("line.separator") + orderDetails;
                    count += 1;
                }
                dataBinding.textViewInsert.setText(allOrders);
            }
        });

        myDatePicker();
        dateBtn = dataBinding.dateBtn;
        dateBtn.setText(getCurrentDate());
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(view);

            }
        });


        myTimePicker();
        timeBtn = dataBinding.TimeBtn;
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(view);
            }
        });

        seekBar = dataBinding.slider;
        textView = dataBinding.textViewSlider;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int rat, boolean b) {
                textView.setText("Please select of number of RATs: " + String.valueOf(rat));
                numRat = String.valueOf(rat);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radioGroup = dataBinding.radio;
        textViewRadio = dataBinding.rBtnText;

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioOne:
                        textViewRadio.setText("\nPlease select test type: Nasal swabs");
                        ratType = "Nasal swabs";
                        break;
                    case R.id.radioTwo:
                        textViewRadio.setText("\nPlease select test type: Pharyngeal swab");
                        ratType = "Pharyngeal swab";
                        break;
                }
            }
        });

        name = dataBinding.editTextTextPersonName;
        phone = dataBinding.editTextPhone;
        button = dataBinding.button2;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderDate.length() == 0)
                {
                    dateBtn.setError("Invalid Date.");
                }

                else if(orderTime.length() == 0)
                {
                    timeBtn.setError("Invalid Time");
                }

                else if(name.length() <= 1 || name.length() >= 15 ||
                        Character.isUpperCase(name.getText().toString().charAt(0)) == false ||
                        isNumeric(name.getText().toString()))
                {
                    name.setError("Invalid input, the input length should be longer than " +
                            "1 and smaller than 15, start with a capital letter.");
                }
                else if(phone.length() != 10)
                {
                    phone.setError("Invalid input, the input should be 10 digits number.");
                }
                else
                {
                    oName = name.getText().toString();
                    oPhone = phone.getText().toString();

                    Order order = new Order(orderDate, orderTime, numRat, ratType, oName, oPhone);

                    orderViewModel.insert(order);


                    orderInfo = orderDate + "," + orderTime + "," + numRat + ","
                            + ratType + "," + oName +
                            "," + oPhone;
                    //dataBinding.editTextTextPersonName.setText(orderInfo);
                }


            }
        });

        return view;

    }

    public static boolean isNumeric(String str)
    {
        for(int i = 0; i < str.length(); i ++)
        {
            int chr = str.charAt(i);
            if(chr < 48 || chr > 57)
            {
                return false;
            }
        }
        return true;
    }


    private void myTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                hour = sHour;
                minute = sMinute;
                timeBtn.setText(String.format(Locale.getDefault(), "%02d:%02d",hour,minute));
                orderTime = String.format(Locale.getDefault(), "%02d:%02d",hour,minute);

            }
        };

        int theme = AlertDialog.THEME_HOLO_LIGHT;
        timePickerDialog = new TimePickerDialog(getActivity(),theme,onTimeSetListener, hour, minute,true);


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
                        orderDate = "";
                    }
                    else
                    {
                        dateBtn.setText(date);
                        orderDate = date;
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

    public void showTimePicker(View view) {
        timePickerDialog.show();
    }
}