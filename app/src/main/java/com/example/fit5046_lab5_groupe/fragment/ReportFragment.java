package com.example.fit5046_lab5_groupe.fragment;
//package xyz.ecoo.www.imagecarouseldemo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.fit5046_lab5_groupe.BottomDialog;
import com.example.fit5046_lab5_groupe.OkHttpUtil;
import com.example.fit5046_lab5_groupe.ReportBean;
import com.example.fit5046_lab5_groupe.databinding.ReportFragmentBinding;
import com.example.fit5046_lab5_groupe.entity.PieBean;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.openxu.cview.chart.bean.BarBean;
import com.openxu.cview.chart.piechart.PieChartLayout;
import com.openxu.utils.DensityUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.Fragment;

//import com.example.fit5046_lab5_groupe.viewmodel.SharedViewModel;
public class ReportFragment extends Fragment {
    private ReportFragmentBinding binding;
    private TextView btnbegin;
    private TextView btnover;
    private Calendar cal;
    private int year, month, day;
    ReportBean reportBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();;
        //SharedViewModel model = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        fetchData();

        binding.chart4.setLoading(true);
        binding.chart4.setBarItemSpace(DensityUtil.dip2px(requireContext(), 40));  //柱间距
        binding.chart4.setBarColor(new int[]{Color.parseColor("#5F93E7")});

        binding.pieChart4.setLoading(true);
        binding.pieChart4.setRingWidth(DensityUtil.dip2px(requireContext(), 0));
        binding.pieChart4.setTagModul(PieChartLayout.TAG_MODUL.MODUL_CHART);
        //get start date   2022-05-13
        getbeginDate();
        btnbegin = binding.btnbegin;
        btnbegin.setOnClickListener(v -> {
            TimePickerView pvTime = new TimePickerBuilder(requireContext(), (date, v1) -> {
                String startTime = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date);
                btnbegin.setText(startTime);
            }).setType(new boolean[]{true, true, true, false, false, false}).build();
            pvTime.show();
        });

        //get end date
        getoverDate();
        btnover = binding.btnover;
        btnover.setOnClickListener(v -> {
            TimePickerView pvTime = new TimePickerBuilder(requireContext(), (date, v1) -> {
                String startTime = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date);
                btnover.setText(startTime);
            }).setType(new boolean[]{true, true, true, false, false, false}).build();
            pvTime.show();
        });

        binding.btnselect.setOnClickListener(view1 -> {

            BottomDialog bottomDialog = new BottomDialog(requireContext());
            bottomDialog.setListener(new BottomDialog.BottomDialogListener() {
                @Override
                public void onPieChart() {
                    binding.chart4.setVisibility(View.GONE);
                    binding.pieChart4.setVisibility(View.VISIBLE);
                    initPie();
                }

                @Override
                public void onColumnChart() {
                    binding.chart4.setVisibility(View.VISIBLE);
                    binding.pieChart4.setVisibility(View.GONE);
                    initNewColumnc();
                }
            });
            bottomDialog.show();
        });

        return view;

    }

    private void fetchData() {
        OkHttpUtil.getInstance().Get("https://api.coronavirus.data.gov.uk/v1/data", new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                Log.e("TAG", "get call failed：");
                //get call failed
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                String body = response.body().string();
                reportBean = new Gson().fromJson(body, ReportBean.class);
            }
        });
    }


    private void getbeginDate() {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
    }


    private void getoverDate() {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
    }

    private void initPie(){
        if(reportBean == null) return;
        List<PieBean> datalist = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long begin = formatter.parse(btnbegin.getText().toString()).getTime();
            long over = formatter.parse(btnover.getText().toString()).getTime();
            for (int i = 0; i < reportBean.getLength(); i++){
                long report = formatter.parse(reportBean.getData().get(i).getDate()).getTime();
                if (begin <= report && over >= report){
                    datalist.add(new PieBean(reportBean.getData().get(i).getLatestBy() == null ? 0 : ((Number) reportBean.getData().get(i).getLatestBy()).floatValue()
                            , reportBean.getData().get(i).getDate()));

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.pieChart4.setLoading(false);
        binding.pieChart4.setChartData(PieBean.class, "Number", "Name",datalist ,null);
    }

    private void initNewColumnc(){
        if(reportBean == null) return;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //X轴
        List<String> strXList = new ArrayList<>();
        //bar char data
        List<List<BarBean>> dataList = new ArrayList<>();
        try {
            long begin = formatter.parse(btnbegin.getText().toString()).getTime();
            long over = formatter.parse(btnover.getText().toString()).getTime();
            for (int i = 0; i < reportBean.getLength(); i++){
                long report = formatter.parse(reportBean.getData().get(i).getDate()).getTime();
                if (begin <= report && over >= report){

                    List<BarBean> list = new ArrayList<>();
                    list.add(new BarBean(reportBean.getData().get(i).getLatestBy() == null ? 0 : ((Number) reportBean.getData().get(i).getLatestBy()).floatValue(), "Number of confirmed cases"));
                    dataList.add(list);
                    strXList.add(reportBean.getData().get(i).getDate());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.chart4.setLoading(false);
        binding.chart4.setData(dataList, strXList);
    }
}
