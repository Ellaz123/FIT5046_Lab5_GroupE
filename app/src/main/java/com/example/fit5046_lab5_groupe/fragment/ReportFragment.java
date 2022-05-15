package com.example.fit5046_lab5_groupe.fragment;
//package xyz.ecoo.www.imagecarouseldemo;

import androidx.fragment.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.fit5046_lab5_groupe.BottomDialog;
import com.example.fit5046_lab5_groupe.OkHttpUtil;
import com.example.fit5046_lab5_groupe.R;
import com.example.fit5046_lab5_groupe.ReportBean;
import com.example.fit5046_lab5_groupe.databinding.ReportFragmentBinding;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.widget.TimePicker;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;


import com.anychart.enums.LegendLayout;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import com.example.fit5046_lab5_groupe.viewmodel.SharedViewModel;
public class ReportFragment extends Fragment {
    private ReportFragmentBinding binding;
    private TextView btnbegin;
    private TextView btnover;
    private Calendar cal;
    private int year, month, day;
    List<DataEntry> data;
    private Pie pie;
    private Column column;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();;
        //SharedViewModel model = new ViewModelProvider(getActivity()).get(SharedViewModel.class);


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
            fetchData();
            bottomDialog.setListener(new BottomDialog.BottomDialogListener() {
                @Override
                public void onPieChart() {
                    //pie chart
                    binding.anyChartView.setVisibility(View.VISIBLE);
                    initPic();
                }

                @Override
                public void onColumnChart() {
                    // column chart
                    binding.anyChartView.setVisibility(View.VISIBLE);
                    initColumnc();
                }
            });
            bottomDialog.show();
        });

        return view;

    }

    private void initPic() {
        binding.anyChartView.setProgressBar(binding.progressBar);
        pie = AnyChart.pie();
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(ReportFragment.this.getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        pie.data(data);

        pie.title("Coronavirus (COVID-19) in the UK");

        pie.labels().position("Date");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("New daily Cases")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        binding.anyChartView.setChart(pie);
    }

    private void initColumnc() {

        binding.anyChartView.setProgressBar(binding.progressBar);
        Cartesian cartesian = AnyChart.column();
        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Coronavirus (COVID-19) in the UK");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Date");
        cartesian.yAxis(0).title("New daily Cases");

        binding.anyChartView.setChart(cartesian);
    }


//    List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("Apples", 6371664));
//        data.add(new ValueDataEntry("Pears", 789622));
//        data.add(new ValueDataEntry("Bananas", 7216301));
////        data.add(new ValueDataEntry("Grapes", 1486621));
//        data.add(new ValueDataEntry("Oranges", 1200000));


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
                ReportBean reportBean = new Gson().fromJson(body, ReportBean.class);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    long begin = formatter.parse(btnbegin.getText().toString()).getTime();
                    long over = formatter.parse(btnover.getText().toString()).getTime();
                    data = new ArrayList<>();
                    for (int i = 0; i < reportBean.getLength(); i++){
                        long report = formatter.parse(reportBean.getData().get(i).getDate()).getTime();
                        if (begin <= report && over >= report){
                            data.add(new ValueDataEntry(reportBean.getData().get(i).getDate(), (Number) reportBean.getData().get(i).getLatestBy()));
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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


}
