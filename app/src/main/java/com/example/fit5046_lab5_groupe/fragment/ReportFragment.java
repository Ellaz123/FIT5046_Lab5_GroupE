package com.example.fit5046_lab5_groupe.fragment;
//package xyz.ecoo.www.imagecarouseldemo;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.app.Activity;
import android.app.DatePickerDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.example.fit5046_lab5_groupe.MainActivity;
import com.example.fit5046_lab5_groupe.databinding.ReportFragmentBinding;
import java.util.Calendar;
//import com.example.fit5046_lab5_groupe.viewmodel.SharedViewModel;
public class ReportFragment extends Fragment {
    private ReportFragmentBinding binding;

    private TextView btnbegin;
    private TextView btnover;
    private Calendar cal;
    private int year,month,day;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ReportFragmentBinding.inflate(inflater,container, false);
        View view = binding.getRoot();
        //SharedViewModel model = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        //获取当前日期  开始
        getbeginDate();

        btnbegin = binding.btnbegin;
        btnbegin.setOnClickListener(v -> {});

        //获取当前日期  结束
        getoverDate();
        btnover=binding.btnover;
        btnover.setOnClickListener(v -> {});
        return view;
    }

    //获取当前日期  开始
    private void getbeginDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }
    //获取当前日期  结束
    private void getoverDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }



}